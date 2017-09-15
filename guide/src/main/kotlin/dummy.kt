// This is dummy file.
//
// This project creates modules in sub-projects to generate multiple entry points.
// Therefore, we do not make `guide` module.
// However, `guide` module is necessary.
//
// If this file does not exist, the following error occurs.
//
// > File '<project_root>/guide/build/classes/main/guide_main.js' specified for property 'sourceFile' does not exist.
//
// `compileKotlin2Js.kotlinOptions.outputFile` is used for module name.
//