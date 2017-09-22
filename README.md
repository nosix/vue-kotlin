[日本語/Japanese](./README_ja.md)

# vue-kotlin
Libraries and tools supporting the use of Vue.js in Kotlin.

- vuekt
    - Type definition of Vue.js for Kotlin/JS
- vuekt-js2vue
    - A library for creating Kotlin files that replace vue files
    - Use Kotlin DSL for CSS
- vuekt-plugin
    - Gradle plugin to support development using vuekt
        - Generate vue files from code written with vuekt-js2vue
        - Extend kotlin-frontend-plugin 

## Usage

### Gradle setting

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
    compile project(':greeting') // application that use vue component
    compile project(':greeting-component') // single file vue component
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
            moduleKind = "commonjs" // webpack use CommonJS
        }
    }
}
```

optional vuekt-plugin settings:

```groovy
vue {
    targetPattern = ".*-component_main\\.js"
    configFile = "01_js2vue.js"
}
```

- targetPattern
    - vuekt-plugin searches targetPattern for JavaScript files generated from ComponentVue
    - JavaScript files generate vue files
- configFile
    - vuekt-plugin generate additional webpack config in webpack.config.d directory
    - This config is required when bundling

### Project structure

For example:

```
project_root
    greeting
        main
            [application: Kotlin]
    greeting-component
        main
            [component: Kotlin]
    src
        main
            kotlin
                dummy.kt (*1)
    webContent
      [web resources: HTML, etc.]
    webpack.config.d
      [webpack config: JavaScript]
```

You need to set webpack config.
See `single-file` project in GitHub repository.

(*1): It is necessary if you have multiple entries in webpack config.
kotlin-frontend-plugin assumes one entry.
By default it does not create sub projects.

### Application

For example:

```kotlin
// GreetingComponent = require('greeting-component.vue')
@JsModule("greeting-component.vue")
external val GreetingComponent: Component = definedExternally

@JsModule("vue") // module name in node_modules
@JsName("Vue") // `AppVue` is converted to the name `Vue` in JavaScript code
external class AppVue(options: ComponentOptions<AppVue>) : Vue {
    var message: String
}

val vm = AppVue(ComponentOptions {
    el = ElementConfig("#app") // ElementConfig is union type
    data = Data(json<AppVue> { // json function create JSON instance
        message = "Vue & Kotlin" // accessible property of AppVue
    })
    components = json {
        this["greeting"] = ComponentConfig(GreetingComponent)
    }
})
```

### Component

For example:

```kotlin
external class GreetingComponent : Vue {
    var greeting: String
}

// class name is used for file name (can be overridden)
class GreetingComponentVue : ComponentVue<GreetingComponent> {

    // vue template (Note: in IntelliJ, editing with inject language will result in syntax highlighting)
    override val template: String = """<p>{{ greeting }} World!</p>"""

    // CSS (it becomes scoped if scopedStyle property is overridden with true)
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
val options = translate(GreetingComponentVue()) // required, used by bundling
```

CSS library: [null-dev/Aza-Kotlin-CSS-JS](https://github.com/null-dev/Aza-Kotlin-CSS-JS)
(MIT License)

Two files are generated:

- greeting-component_main.js (kotlin2js generated)
- greeting-component.vue (js2vue generated)

Generated vue file (greeting-component.vue):

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

`options` in the component is used in the vue file.

### Data flow

<img src="https://docs.google.com/drawings/d/e/2PACX-1vQIrWUsJ0aY3VlbUkyvkIdA6Z4yikCmfxxPr_nYKUKZsbmiG0WV8qR_tEY4SFgA8LMwZoKh2QMuU90Z/pub?w=960&amp;h=720">

- `greeting-component_main.js` and `greeting-component.vue` are named from `GreetingComponentVue`
    - `Vue` of prefix/suffix is removed
    - Camel case is converted to kebab case
    - You can change the name by overriding ComponentVue::name property
        - default is `this::class.js.name.replace("^Vue|Vue$".toRegex(), "").replace("([A-Z])".toRegex(), "-$1").toLowerCase().trim('-')`
- vuekt-js2vue finds `*-component_main.js`
    - You can change the pattern by setting targetPattern in build.gradle

## Trial

1. Clone this repository.

1. Publish plugin to local repository.

    ```
    $ ./gradlew --project-dir=vuekt-plugin publishToMavenLocal
    ```

Try production build (minify is still)

1. Generate bundle files.

    ```
    $ ./gradlew bundle
    ```

1. Open the following in a browser.

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

Try development build

1. Run webpack-dev-server.
(`-t` is option for [continuous build](https://docs.gradle.org/current/userguide/continuous_build.html))

    ```
    $ ./gradlew -t single-file:run
    ```

1. Open `http://localhost:8088/` on the browser.

    HMR (Hot Module Replacement) is enabled.
    (Edit `greeting/main/greeting.kt` and/or `greeting-component/main/GreetingComponent.kt`)

1. Stop webpack-dev-server.

    ```
    $ ./gradlew single-file:stop
    ```

## Troubleshooting

1. Execution failed for task webpack-bundle: `node webpack.js failed`

    Please try the following script:
    ```
    $ bin/webpack.sh
    ```

1. Changing `vuekt` and/or `vuekt-js2vue` don't update `guide` and `single-file` project.

    kotlin-frontend-plugin does not update node_modules.
    
    Please try the following command:
    ```
    $ ./gradlew clean
    ```
