# plantuml-image-generator

PlantUML文書を渡すと画像を返すAPI

## Description

GitHubのようなオンラインにあるPlantUML文書のURLを渡すだけで画像を返すAPIが欲しい。ブログに埋め込みたい。PlantUML Serverというサービスもありますが、あれはPlantUML文書を全文入力しなければならないので、例えばGitHub側で修正しても、画像に反映されません。

## Usage

こんな感じで、PlantUML文書のURLを指定して、画像を表示したい。

```
<img src="https://xxx.com/image.png?url=http://github.com/xxx.pu">
```

## Author

- [u6k - GitHub](https://github.com/u6k)

## License

MIT License
