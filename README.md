![](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white) ![](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white) ![](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)

# Generic Expandable Adapter
<img src="/giffs/default-with-background.gif" width="30%" height="30%"/> <img src="/giffs/default-no-background.gif" width="30%" height="30%"/> <img src="/giffs/custom.gif" width="30%" height="30%"/>

Esta biblioteca fornece uma abstração com tipos genéricos que facilita a implementação de um adapter expansível, podendo ser feito por meio de herança ou através  de uma extension. Além disso, ela também fornece uma implementação padrão de um adapter expansível customizado. Esta abstração foi baseada no artigo de [HOUSSEIN OUERGHEMMI](https://github.com/OHoussein) intitulado [Create an expandable RecyclerView with the ConcatAdapter](https://medium.com/codeshake/create-an-expandable-recyclerview-with-the-mergeadapter-254fd671fa5b).

## Instalação

### Adicione o repositório ```jitpack.io``` no build.gradle (app)

 ```groove
allprojects {
    repositories {
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}
```

### Adicione a dependência da biblioteca

```groove
implementation 'com.github.romullodev:generic-expandable-adapter:1.0.0'
```

## Implementação Padrão

Utilize as classes ```CardHeaderModel``` e ```CardItemModel``` como modelo para o cabeçalho e seus itens, respectivamente. A estilização pode ser customizada através da classe ```CardHeaderStyle``` para o cabeçalho e da classe ```CardItemStyle``` para os itens. O código abaixo exemplifica uma implementação utilizando a extension ```setupDefaultExpandableAdapter```.

```kotlin
fun setupAdapterByDefault() {
    binding.recyclerViewExpandableAdapterDemo.setupDefaultExpandableAdapter(
        dataHeaders = MockData.getMusicsWithBackground()
    )
}

fun getMusicsWithBackground(): List<CardHeaderModel> = listOf(
    CardHeaderModel(
        cardName = "Rock",
        items = getRockBands(),
        cardHeaderStyle = CardHeaderStyle(
            backgroundImgRes = R.drawable.rock,
            backgroundColorItems = R.color.rock_color
        )
    ),
)

fun getRockBands(): List<CardItemModel> = listOf(
    CardItemModel(
        itemName = "Arctic Monkeys",
        cardItemStyle = CardItemStyle(
            backgroundColorRes = R.color.green,
            thicknessColor = R.color.pop_color
        )
    ),
)
```

## Implementação por Herança

Basta extender seu adapter de ```BaseExpandableAdapter```, fornecendo as classes customizadas que representam o cabeçalho e itens, além dos seus respectivos layouts. Por fim, a instância de cada adapter deve ser combinada utilizando a classe [ConcatAdapter](https://developer.android.com/reference/androidx/recyclerview/widget/ConcatAdapter) a qual deverá ser atribuída no [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview?gclid=Cj0KCQjwlumhBhClARIsABO6p-w0BtlEpsfWXBiGziDom3nl8WCiwAahAw-iKgCfCryNKLMWXsm5A_YaAgRVEALw_wcB&gclsrc=aw.ds&hl=pt-br).

Abaixo temos um exemplo dessa implementação

### Adapter
```kotlin
class CustomExpandableAdapter(
    header: CustomHeaderModel
): BaseExpandableAdapter<CustomHeaderModel, CustomItemModel>(
    headerObject = header,
    headerLayoutRes = R.layout.custom_header,
    itemLayoutRes = R.layout.custom_item
) {
    override fun getItems(headerObject: CustomHeaderModel): List<CustomItemModel>  =
        headerObject.items

    override fun getItemBindingCallback(): ItemBindingCallback<CustomItemModel, CustomHeaderModel> =
        { item, _, itemBinding ->
            (itemBinding as? CustomItemBinding)?.run {
                textViewCustomItemTitle.text = item.customItemName
            }
        }

    override fun getHeaderBindingCallback(): HeaderBindingCallback<CustomHeaderModel> =
        { header, headerBinding ->
            (headerBinding as? CustomHeaderBinding)?.run {
                textViewCustomTitle.text = header.customHeaderName
            }
        }

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
        (headerBinding as? CustomHeaderBinding)?.imageViewArrowDown
}
```

### Modelos

```kotlin
data class CustomHeaderModel(
    val customHeaderName: String,
    val items: List<CustomItemModel>
)

data class CustomItemModel(
    val customItemName: String
)
```

### Atribuição no RecyclerView

```kotlin
private fun setupCustomAdapterByAdapter() {
    binding.recyclerViewExpandableAdapterDemo.run {
        MockData.getCustomHeader().map {
            CustomExpandableAdapter(
                header = it,
            )
        }.let {
            ConcatAdapter.Config.Builder()
                .setIsolateViewTypes(false)
                .build().run {
                    ConcatAdapter(this, it).also {
                        adapter = it
                        itemAnimator = ExpandableAdapterAnimation()
                    }
                }
        }
    }
}
```
## Figma

Clique [aqui](https://www.figma.com/file/TLMcrevEtPfjVEfQYiEimz/Expandable-Adapter?node-id=0%3A1&t=LUOn0nOcr146XC8k-1) para obter acesso as imagens criadas nos exemplos de demonstração.

## Licença MIT

Copyright 2023 Rômulo Silva

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

