# plantuml-image-generator

[![CircleCI](https://img.shields.io/circleci/project/github/u6k/plantuml-image-generator.svg)](https://circleci.com/gh/u6k/plantuml-image-generator) [![license](https://img.shields.io/github/license/u6k/plantuml-image-generator.svg)](https://github.com/u6k/plantuml-image-generator/blob/master/LICENSE) [![GitHub release](https://img.shields.io/github/release/u6k/plantuml-image-generator.svg)](https://github.com/u6k/plantuml-image-generator/releases) [![Docker Pulls](https://img.shields.io/docker/pulls/u6kapps/plantuml-image-generator.svg)](https://hub.docker.com/r/u6kapps/plantuml-image-generator/)

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

### CircleCI CLIでビルド確認

- [Using the CircleCI Command Line Interface (CLI) - CircleCI](https://circleci.com/docs/2.0/local-jobs/)

CircleCI CLIをインストールして、以下を実行します。

```
$ circleci build
```

## Author

- [u6k - GitHub](https://github.com/u6k)

## License

[![MIT License](https://img.shields.io/github/license/u6k/plantuml-image-generator.svg)](https://github.com/u6k/plantuml-image-generator/blob/master/LICENSE)
