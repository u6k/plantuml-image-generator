# plantuml-image-generator

[![Travis](https://img.shields.io/travis/u6k/plantuml-image-generator.svg)](https://travis-ci.org/u6k/plantuml-image-generator)
[![GitHub release](https://img.shields.io/github/release/u6k/plantuml-image-generator.svg)](https://github.com/u6k/plantuml-image-generator/releases)
[![license](https://img.shields.io/github/license/u6k/plantuml-image-generator.svg)](https://github.com/u6k/plantuml-image-generator/blob/master/LICENSE)
[![Docker Stars](https://img.shields.io/docker/stars/u6kapps/plantuml-image-generator.svg)](https://hub.docker.com/r/u6kapps/plantuml-image-generator/)
[![project-reports](https://img.shields.io/badge/site-project--reports-orange.svg)](https://u6k.github.io/plantuml-image-generator/project-reports.html)
[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

> PlantUML文書が存在するURL渡すと画像を返すWebAPIです。

ブログにPlantUML画像を埋め込むときに、PlantUML文書から画像ファイルを生成してその画像ファイルをimgタグに指定することは、少々メンドウです。できれば、GistにPlantUML文書をアップロードして、URLを指定するだけでPlantUML画像を表示したいです(画像生成を省略したい)。それを実現するために、plantuml-image-generatorを構築しました。

plantuml-image-generatorにPlantUML文書のURLを渡すと画像を生成します。詳しくは[Demo](#demo)をご覧ください。

## Table of Contents

<!-- TOC depthFrom:2 -->

- [Table of Contents](#table-of-contents)
- [Install](#install)
- [Usage](#usage)
- [Demo](#demo)
- [API](#api)
- [Maintainer](#maintainer)
- [Contribute](#contribute)
- [License](#license)

<!-- /TOC -->

## Install

Dockerイメージを配布しているため、次のように実行することができます。

```
$ docker run \
    -p 8080:8080 \
    u6kapps/plantuml-image-generator
```

jarファイルを実行することもできます。

```
$ java -jar plantuml-image-generator-x.x.x.jar
```

## Usage

`/images`エンドポイントにURLを渡すと、画像が生成されます。この時、URLパラメータはURLエンコードしてください。

例えば、[https://goo.gl/ERJqwh](https://goo.gl/ERJqwh)の画像を生成する場合は、次のようにアクセスします。

- http://localhost:8080/images?url=https%3A%2F%2Fgoo.gl%2FERJqwh

HTML文書に埋め込む場合は、次のようにimgタグを書きます。

```
<img src="http://localhost:8080/images?url=https%3A%2F%2Fgoo.gl%2FERJqwh">
```

Markdown文書に埋め込む場合は、次のように書きます。

```
![](http://localhost:8080/images?url=https%3A%2F%2Fgoo.gl%2FERJqwh)
```

## Demo

デモ・サービスとして、`plantuml-image-generator.u6k.me`が動作しています。次のURLにアクセスしてください。実際に画像が生成されます。

- https://plantuml-image-generator.u6k.me/images?url=https%3A%2F%2Fgoo.gl%2FERJqwh

## API

WebAPI仕様は、次のURLで公開しています。

- https://u6k.github.io/plantuml-image-generator/swagger/document.html

Javadocは、次のURLで公開しています。

- https://u6k.github.io/plantuml-image-generator/apidocs/index.html

WebAPI仕様やJavadocを含むプロジェクト・レポートは、次のURLで公開しています。

- https://u6k.github.io/plantuml-image-generator/index.html

## Maintainer

- [u6k - GitHub](https://github.com/u6k/)
- [u6k.Blog()](https://blog.u6k.me/)
- [u6k_yu1 | Twitter](https://twitter.com/u6k_yu1)

## Contribute

貴重なアイデアをご提案頂ける場合は、Issueを書いていただけると幸いです。あなたは、このプロジェクトに参加することによって、[Open Source Code of Conduct - thoughtbot](https://thoughtbot.com/open-source-code-of-conduct)を遵守することに同意します。

次に、このプロジェクトで開発を行う手順を説明します。

Eclipseプロジェクトを作成:

```
$ ./mvnw eclipse:eclipse
```

開発用Dockerイメージをビルド:

```
$ docker build -t plantuml-image-generator-dev -f Dockerfile-dev .
```

ユニット・テスト:

```
$ docker run \
    --rm \
    -v ${HOME}/.m2:/root/.m2 \
    -v ${PWD}:/var/plantuml-image-generator \
    plantuml-image-generator-dev \
        ./mvnw clean surefire-report:report
```

動作確認

```
$ docker run \
    --rm \
    -v ${HOME}/.m2:/root/.m2 \
    -v ${PWD}:/var/plantuml-image-generator \
    -p 8080:8080 \
    plantuml-image-generator-dev \
        ./mvnw clean spring-boot:run
```

E2Eテスト:

__TODO:__ E2Eテストは未実装です。

実行用Dockerイメージを構築:

```
$ docker build -t plantuml-image-generator .
```

実行:

```
$ docker run \
    -p 8080:8080 \
    plantuml-image-generator
```

E2Eテスト:

```
# バージョン情報を表示
curl -v http://localhost:8080/info

# Gistにある各図をpng生成してみる
curl -v -o activity1.png 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Factivity1.pu'
curl -v -o activity2.png 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Factivity2.pu'
curl -v -o class.png 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Fclass.pu'
curl -v -o component.png 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Fcomponent.pu'
curl -v -o deployment.png 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Fdeployment.pu'
curl -v -o object.png 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Fobject.pu'
curl -v -o sequence.png 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Fsequence.pu'
curl -v -o state.png 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Fstate.pu'
curl -v -o test.png 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Ftest.pu'
curl -v -o timing.png 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Ftiming.pu'
curl -v -o usecase.png 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Fusecase.pu'

# Gistにある各図をsvg生成してみる
curl -v -o activity1.svg 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Factivity1.pu&format=svg'
curl -v -o activity2.svg 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Factivity2.pu&format=svg'
curl -v -o class.svg 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Fclass.pu&format=svg'
curl -v -o component.svg 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Fcomponent.pu&format=svg'
curl -v -o deployment.svg 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Fdeployment.pu&format=svg'
curl -v -o object.svg 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Fobject.pu&format=svg'
curl -v -o sequence.svg 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Fsequence.pu&format=svg'
curl -v -o state.svg 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Fstate.pu&format=svg'
curl -v -o test.svg 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Ftest.pu&format=svg'
curl -v -o timing.svg 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Ftiming.pu&format=svg'
curl -v -o usecase.svg 'http://localhost:8080/images?url=https%3A%2F%2Fgist.github.com%2Fu6k%2F4208e71ad62d3b972c357c77783cb95d%2Fraw%2F01c544cab40009c723d57be3d892092ef35fac6e%2Fusecase.pu&format=svg'
```

## License

[GPL v3 &copy; 2017 u6k.apps@gmail.com](https://github.com/u6k/plantuml-image-generator/blob/master/LICENSE)
