# vue-kotlin
Type definition of Vue.js for Kotlin/JS.

## Install

In development...

## Trial

Publish plugin to local repository.

```
$ ./gradlew --project-dir=vuekt-plugin publishToMavenLocal
```

Generate bundle files.

```$shell
$ ./gradlew bundle
```

Open the following in a browser.
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

Run webpack-dev-server.
(`-t` is option for [continuous build](https://docs.gradle.org/current/userguide/continuous_build.html))

```$shell
$ ./gradlew -t single-file:run
```

Open `http://localhost:8088/` on the browser.

HMR (Hot Module Replacement) is enabled.
(Edit `greeting/main/greeting.kt` and/or `greeting-component/main/GreetingComponent.kt`)

Stop webpack-dev-server.

```$shell
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