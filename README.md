# plantuml-image-generator

[![Travis](https://img.shields.io/travis/u6k/plantuml-image-generator.svg)](https://travis-ci.org/u6k/plantuml-image-generator) [![GitHub release](https://img.shields.io/github/release/u6k/plantuml-image-generator.svg)](https://github.com/u6k/plantuml-image-generator/releases) [![license](https://img.shields.io/github/license/u6k/plantuml-image-generator.svg)](https://github.com/u6k/plantuml-image-generator/blob/master/LICENSE) [![Docker Stars](https://img.shields.io/docker/stars/u6kapps/plantuml-image-generator.svg)](https://hub.docker.com/r/u6kapps/plantuml-image-generator/) [![project-reports](https://img.shields.io/badge/site-project--reports-orange.svg)](https://u6k.github.io/plantuml-image-generator/project-reports.html)

PlantUML文書を渡すと画像を返すAPI

## Description

GitHubのようなオンラインにあるPlantUML文書のURLを渡すだけで画像を返すAPIが欲しい。ブログに埋め込みたい。PlantUML Serverというサービスもありますが、あれはPlantUML文書を全文入力しなければならないので、例えばGitHub側で修正しても、画像に反映されません。

## DEMO

TODO

## Features

- PlantUML文書のURLを指定して、画像データ(png/svg)を返します。

## Usage

こんな感じで、PlantUML文書のURLを指定して、画像を表示したい。

```
<img src="https://xxx.com/image.png?url=http://github.com/xxx.pu">
```

## Installation

TODO

## Development

### 開発環境を構築

Eclipseプロジェクトを作成します。

```
$ ./gradlew eclipse
```

実行します。

```
$ ./gradlew bootRun
```

## Author

- [u6k - GitHub](https://github.com/u6k)

## License

[![GPL v3](https://img.shields.io/github/license/u6k/plantuml-image-generator.svg)](https://github.com/u6k/plantuml-image-generator/blob/master/LICENSE)
