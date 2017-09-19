# vue-kotlin
Type definition of Vue.js for Kotlin/JS.

## Install

In development...

## Trial

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