[English](./README.md)

# vue-kotlin
Kotlin での Vue.js の使用を支援するライブラリとツール

- vuekt
    - Kotlin/JS のための Vue.js の型定義
- vuekt-js2vue
    - vue ファイルの代わりとなる Kotlin ファイルを作るためのライブラリ
    - CSS には Kotlin DSL を使用
- vuekt-plugin
    - vuekt を使った開発を支援する Gradle プラグイン
        - vuekt-js2vue を使って書かれたコードから vue ファイルを生成
        - kotlin-frontend-plugin を拡張 

## 使用方法

### Gradle の設定

```groovy
buildscript {
    repositories {
        jcenter()
        maven {
            // kotlin-frontend-plugin (vuekt-plugin depends)
            url "https://dl.bintray.com/kotlin/kotlin-eap"
        }
        maven {
            // vuekt-plugin
            url "https://nosix.github.io/vue-kotlin/release"
        }
    }

    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath "org.jetbrains.kotlin:kotlin-frontend-plugin:$kotlin_frontend_version"
        classpath "org.musyozoku:vuekt-plugin:$vuekt_plugin_version"
    }
}

repositories {
    jcenter()
    maven {
        // vuekt, vuekt-js2vue
        url "https://nosix.github.io/vue-kotlin/release"
    }
}

apply plugin: 'kotlin2js'
apply plugin: 'org.jetbrains.kotlin.frontend' // kotlin-frontend-plugin
apply plugin: 'org.musyozoku.vuekt' // vuekt-plugin

dependencies {
    // sub projects
    compile project(':greeting') // vue コンポーネントを使うアプリケーション
    compile project(':greeting-component') // 単一ファイル vue コンポーネント
}

kotlinFrontend {
    downloadNodeJsVersion = node_version

    npm {
        dependency('vue', vue_version)

        devDependency('vue-loader', '*')
        devDependency('vue-template-compiler', vue_version)
        devDependency('css-loader', '*')
        devDependency('html-webpack-plugin', '*')
    }

    webpackBundle {
        contentPath = file("$projectDir/webContent")
        publicPath = "/"
        port = 8088
    }
}

subprojects {

    apply plugin: 'kotlin2js'
    
    dependencies {
        compile "org.musyozoku:vuekt:$vuekt_version"
        compile "org.musyozoku:vuekt-js2vue:$vuekt_js2vue_version"
    }
    
    sourceSets {
        main.kotlin.srcDirs += "main"
        test.kotlin.srcDirs += "test"
    }

    compileKotlin2Js {
        kotlinOptions {
            moduleKind = "commonjs" // webpack は CommonJS を使用
        }
    }
}
```

必要に応じて行う vuekt-plugin の設定:

```groovy
vue {
    targetPattern = ".*-component_main\\.js"
    configFile = "01_js2vue.js"
}
```

- targetPattern
    - vuekt-plugin は ComponentVue から生成された JavaScript ファイルを targetPattern によって探します
    - (検索された) JavaScript ファイルは vue ファイルを生成します
- configFile
    - vuekt-plugin は webpack.config.d ディレクトリに追加の webpack config を生成します
    - この config は bundle するときに必要とされます

### プロジェクト構造

例:

```
project_root
    greeting
        main
            [アプリケーション: Kotlin]
    greeting-component
        main
            [コンポーネント: Kotlin]
    src
        main
            kotlin
                dummy.kt (*1)
    webContent
      [ウェブリソース: HTML, etc.]
    webpack.config.d
      [webpack config: JavaScript]
```

webpack config の設定が必要です。
GitHub リポジトリの `single-file` プロジェクトを参照してください。

(*1): webpack config にて複数の entry を持たせる際に必要となります。
kotlin-frontend-plugin は単一の entry を前提としています。
前提としては、サブプロジェクトを作りません。

### アプリケーション

例:

```kotlin
// GreetingComponent = require('greeting-component.vue')
@JsModule("greeting-component.vue")
external val GreetingComponent: Component = definedExternally

@JsModule("vue") // node_modules でのモジュール名
@JsName("Vue") // `AppVue` は JavaScript コードでは `Vue` に変換される
external class AppVue(options: ComponentOptions<AppVue>) : Vue {
    var message: String
}

val vm = AppVue(ComponentOptions {
    el = ElementConfig("#app") // ElementConfig は union 型
    data = Data(json<AppVue> { // json 関数は JSON インスタンスを生成
        message = "Vue & Kotlin" // AppVue のプロパティにアクセス可能
    })
    components = json {
        this["greeting"] = ComponentConfig(GreetingComponent)
    }
})
```

### コンポーネント

例:

```kotlin
external class GreetingComponent : Vue {
    var greeting: String
}

// クラス名はファイル名として使用される (オーバーライド可能)
class GreetingComponentVue : ComponentVue<GreetingComponent> {

    // vue テンプレート (備考: IntelliJ では、inject language を使用して編集するとシンタックスハイライトされる)
    override val template: String = """<p>{{ greeting }} World!</p>"""

    // CSS (scopedStyle プロパティを true でオーバーライドすると scoped になる)
    override val style: StyleBuilder = {
        p {
            fontSize = 2.em
            textAlign = center
        }
    }

    override val script: ComponentOptionsBuilder<GreetingComponent> = {
        data = Data {
            json<GreetingComponent> {
                greeting = "Hello"
            }
        }
    }
}

@Suppress("unused")
val options = translate(GreetingComponentVue()) // 必須、bundle するときに使用
```

CSS library: [null-dev/Aza-Kotlin-CSS-JS](https://github.com/null-dev/Aza-Kotlin-CSS-JS)
(MIT License)

２つのファイルが生成される:

- greeting-component_main.js (kotlin2js が生成)
- greeting-component.vue (js2vue が生成)

生成された vue ファイル (greeting-component.vue):

```html
<template>
<p>{{ greeting }} World!</p>
</template>

<style>
p{font-size:2em;text-align:center}
</style>

<script>
module.exports = require('greeting-component_main.js').options
</script>
```

コンポーネントの `options` は vue ファイルで使用されます。

### データフロー

<img src="https://docs.google.com/drawings/d/e/2PACX-1vQIrWUsJ0aY3VlbUkyvkIdA6Z4yikCmfxxPr_nYKUKZsbmiG0WV8qR_tEY4SFgA8LMwZoKh2QMuU90Z/pub?w=960&amp;h=720">

- `greeting-component_main.js` と `greeting-component.vue` は `GreetingComponentVue` から名付けられます
    - 前置/後置の `Vue` は削除されます
    - キャメルケースはケバブケースに変換されます
    - ComponentVue::name プロパティをオーバーライドすることで名前を変更できます
        - デフォルト: `this::class.js.name.replace("^Vue|Vue$".toRegex(), "").replace("([A-Z])".toRegex(), "-$1").toLowerCase().trim('-')`
- vuekt-js2vue は `*-component_main.js` を探します
    - build.gradle の targetPattern を設定することで検索パターンを変更できます

## 試行方法

1. このリポジトリを clone します

1. ローカルリポジトリにプラグインを配信します

    ```
    $ ./gradlew --project-dir=vuekt-plugin publishToMavenLocal
    ```

製品ビルドを試す (縮小化は未対応)

1. バンドルファイルを生成します

    ```
    $ ./gradlew bundle
    ```

1. 以下のファイルをブラウザで開きます

    - `guide/index.html`
    - `guide/instance.html`
    - `guide/syntax.html`
    - `guide/computed.html`
    - `guide/class-and-style.html`
    - `guide/list.html`
    - `guide/events.html`
    - `guide/forms.html`
    - `guide/components.html`
    - `guide/filters.html`
    - `single-file/build/bundle/greeting.html`

開発ビルドを試す

1. webpack-dev-server を起動します
(`-t` は [continuous build](https://docs.gradle.org/current/userguide/continuous_build.html) のためのオプション)

    ```
    $ ./gradlew -t single-file:run
    ```

1. ブラウザで `http://localhost:8088/` を開きます

    HMR (Hot Module Replacement) が有効です
    (`greeting/main/greeting.kt` と `greeting-component/main/GreetingComponent.kt` を編集します)

1. webpack-dev-server を停止します

    ```
    $ ./gradlew single-file:stop
    ```

## トラブルシューティング

1. Execution failed for task webpack-bundle: `node webpack.js failed`

    以下のスクリプトを試してください:
    ```
    $ bin/webpack.sh
    ```

1. `vuekt` と `vuekt-js2vue` を変更しても `guide` と `single-file` プロジェクトが更新されない

    kotlin-frontend-plugin が node_modules を更新しません
    
    以下のコマンドを試してください:
    ```
    $ ./gradlew clean
    ```
