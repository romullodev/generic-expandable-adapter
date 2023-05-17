![](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white) ![](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white) ![](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)

# Generic Expandable Adapter
<img src="/giffs/default-with-background.gif" width="30%" height="30%"/> <img src="/giffs/default-no-background.gif" width="30%" height="30%"/> <img src="/giffs/custom.gif" width="30%" height="30%"/>

Esta biblioteca fornece uma abstração com tipos genéricos que facilita a implementação de um adapter expansível, podendo ser feito por meio de herança ou através  de uma extension. Além disso, ela também fornece uma implementação padrão de um adapter expansível, em que é possível inserir botões de ação em menus relacionados aos cabeçalhos e seus respectivos itens. A abstração do adapter genérico foi baseada no artigo de [HOUSSEIN OUERGHEMMI](https://github.com/OHoussein) intitulado [Create an expandable RecyclerView with the ConcatAdapter](https://medium.com/codeshake/create-an-expandable-recyclerview-with-the-mergeadapter-254fd671fa5b). Para as ações/opções referente aos itens, foi utilizada a biblioteca
[SwipeRevealLayout](https://github.com/FarhamHosseini/SwipeRevealLayout).

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
implementation 'com.github.romullodev:generic-expandable-adapter:1.1.0'
```

## Implementação Default via Extension

Utilize as classes ```CardHeaderModel``` e ```CardItemModel``` como modelo para o cabeçalho e seus itens, respectivamente. Para os botões de ação no menu, crie uma lista de ```DefaultSwipeOption```, passando para o parâmetro ```optionsOnHeader``` e/ou ```optionsOnItem``` da extension ```setupDefaultExpandableAdapter```.

A estilização pode ser customizada através da classe ```CardHeaderStyle``` para o cabeçalho e da classe ```CardItemStyle``` para os itens. Ainda na parte de estilos, a classe ```LayoutOptions``` também pode ser usada para configurações globais do cabeçalho e itens como, por exemplo, a cor do stroke.

O código abaixo exemplifica uma implementação utilizando a extension ```setupDefaultExpandableAdapter``` para configurar o adapter. As extensions ```removeHeaderDefaultExpandableAdapter``` e ```removeItemDefaultExpandableAdapter```, também fornecidas pela biblioteca, foram usadas para as ações de deleção do cabeçalho e itens.

```kotlin
    private fun setupDefaultExpandableAdapter() {
        binding.recyclerViewExpandableAdapterDemo.setupDefaultExpandableAdapter(
            dataHeaders = MockData.getMusics(requireContext(), hasBackgroundImg = true),
            optionsOnHeader = getDefaultOptionsOnHeader(),
            optionsOnItem = getDefaultOptionsOnItem(),
            layoutOptions = LayoutOptions.DEFAULT
        ) { optionId, model->
            when (optionId) {
                HEADER_SWIPE_DELETE_ID -> {
                    binding.recyclerViewExpandableAdapterDemo.removeHeaderDefaultExpandableAdapter(header = model as CardHeaderModel)
                        .takeIf { it }?.let {
                        Toast.makeText(
                            requireContext(),
                            "${model.headerTitle} deleted", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                HEADER_SWIPE_EDIT_ID ->
                    Toast.makeText(
                        requireContext(),
                        "${(model as CardHeaderModel).headerTitle} edited",
                        Toast.LENGTH_LONG
                    ).show()
                ITEM_SWIPE_DELETE_ID -> {
                    binding.recyclerViewExpandableAdapterDemo.removeItemDefaultExpandableAdapter(item = model as CardItemModel)
                        .takeIf { it }?.let {
                        Toast.makeText(
                            requireContext(),
                            "${model.itemTitle} deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
    
    private fun getDefaultOptionsOnHeader(): List<DefaultSwipeOption> =
        listOf(
            DefaultSwipeOption(
                icon = R.drawable.ic_delete,
                iconColor = R.color.white,
                backgroundColor = R.color.holo_red_dark,
                optionId = HEADER_SWIPE_DELETE_ID,
                width = R.dimen.my_custom_swipe_options_width_on_header
            ),
            DefaultSwipeOption(
                icon = R.drawable.ic_edit,
                iconColor = R.color.white,
                backgroundColor = R.color.darker_gray,
                optionId = HEADER_SWIPE_EDIT_ID,
                width = R.dimen.my_custom_swipe_options_width_on_header
            )
        )

    private fun getDefaultOptionsOnItem(): List<DefaultSwipeOption> = listOf(
        DefaultSwipeOption(
            icon = R.drawable.ic_delete,
            iconColor = R.color.white,
            backgroundColor = R.color.holo_red_dark,
            optionId = ITEM_SWIPE_DELETE_ID,
            width = R.dimen.my_custom_swipe_options_width_on_item
        )
    )
```

## Implementação Customizada via Herança

Após extender seu adapter de ```BaseExpandableAdapter```, forneça as suas classes customizadas que representam o cabeçalho e itens, além dos seus respectivos layouts com suas classes binding devidamente geradas para cada xml. O seu modelo customizado que representa o cabeçalho precisa implementar a interface ```BaseHeaderModel```. Já o seu modelo que representa o item deve implementar a interface ```BaseItemModel```.

Com relação aos botões de ação, crie uma lista de ```GenericSwipeOption``` e passe no construtor da classe abstrada ```BaseExpandableAdapter```. A classe ```GenericSwipeOption```, diferentemente da classe ```DefaultSwipeOption```, oferece os campos ```height``` e ```radius``` para um maior nível de customização.

Por fim, a instância de cada adapter deve ser combinada utilizando a classe [ConcatAdapter](https://developer.android.com/reference/androidx/recyclerview/widget/ConcatAdapter) a qual deverá ser atribuída no [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview?gclid=Cj0KCQjwlumhBhClARIsABO6p-w0BtlEpsfWXBiGziDom3nl8WCiwAahAw-iKgCfCryNKLMWXsm5A_YaAgRVEALw_wcB&gclsrc=aw.ds&hl=pt-br).

Abaixo temos um exemplo dessa implementação por herança:

### Adapter
```kotlin
class MyExpandableAdapter(
    header: MyCustomHeaderModel,
    private val onSwipeOption: OnSwipeOption
) : BaseExpandableAdapter<MyCustomHeaderModel, MyCustomItemModel>(
    data = header,
    layoutOptions = LayoutOptions.DEFAULT,
    optionsOnHeader = getCustomSwipeOptionsOnHeader(),
    optionsOnItem = getCustomSwipeOptionsOnItem()
) {
    override val headerLayoutRes: Int get() = R.layout.my_custom_header
    override val itemLayoutRes: Int get() = R.layout.my_custom_item

    override fun onBindingItem(): OnBindingItem<BaseItemModel, BaseHeaderModel<MyCustomHeaderModel, MyCustomItemModel>> =
        { item: BaseItemModel, _, itemBinding ->
            (itemBinding as? MyCustomItemBinding)?.run {
                textViewCustomItemTitle.text = (item as MyCustomItemModel).myCustomItemName
            }
        }

    override fun onBindingHeader(): OnBindingHeader<BaseHeaderModel<MyCustomHeaderModel, MyCustomItemModel>> =
        { header: BaseHeaderModel<MyCustomHeaderModel, MyCustomItemModel>, headerBinding ->
            (headerBinding as? MyCustomHeaderBinding)?.run {
                textViewCustomTitle.text = (header as MyCustomHeaderModel).myCustomHeaderName
            }
        }

    override fun getExpandedIcImageView(headerBinding: ViewDataBinding): ImageView? =
        (headerBinding as? MyCustomHeaderBinding)?.imageViewArrowDown

    override fun onSwipeOption(): OnSwipeOption =
        onSwipeOption

    companion object {
        private fun getCustomSwipeOptionsOnHeader(): List<GenericSwipeOption> =
            listOf(
                GenericSwipeOption(
                    icon = R.drawable.ic_delete,
                    iconColor = R.color.white,
                    backgroundColor = R.color.holo_red_dark,
                    optionId = MockData.HEADER_SWIPE_DELETE_ID,
                    width = R.dimen.my_custom_width_header_option,
                    height = R.dimen.my_custom_height_header,
                    radius = R.dimen.my_custom_radius_option,
                ),
                GenericSwipeOption(
                    icon = R.drawable.ic_edit,
                    iconColor = R.color.white,
                    backgroundColor = R.color.darker_gray,
                    optionId = MockData.HEADER_SWIPE_EDIT_ID,
                    width = R.dimen.my_custom_width_header_option,
                    height = R.dimen.my_custom_height_header,
                    radius = R.dimen.my_custom_radius_option,
                )
            )

        private fun getCustomSwipeOptionsOnItem(): List<GenericSwipeOption> =
            listOf(
                GenericSwipeOption(
                    icon = R.drawable.ic_delete,
                    iconColor = R.color.white,
                    backgroundColor = R.color.holo_red_dark,
                    optionId = MockData.ITEM_SWIPE_DELETE_ID,
                    width = R.dimen.my_custom_width_header_option,
                    height = R.dimen.my_custom_height_item,
                    radius = R.dimen.my_custom_radius_option,
                ),
            )
    }
}
```

### Modelos

```kotlin
data class MyCustomHeaderModel(
    val myCustomHeaderId: Long,
    val myCustomHeaderName: String,
    val items: List<MyCustomItemModel>,
) : BaseHeaderModel<MyCustomHeaderModel, MyCustomItemModel> {
    override fun getCustomItems(): List<MyCustomItemModel> = items
    override fun getModelId(): Long = myCustomHeaderId
    override fun getHeaderWithUpdatedItems(newItems: List<MyCustomItemModel>): MyCustomHeaderModel =
        this.copy(items = newItems)

    override fun isEqualTo(model: Any): Boolean =
        this == (model as MyCustomHeaderModel)
}

data class MyCustomItemModel(
    val myCustomItemId: Long,
    val myCustomItemName: String
) : BaseItemModel {
    override fun getModelId(): Long = myCustomItemId
    override fun isEqualTo(model: Any): Boolean = this == (model as MyCustomItemModel)
    override fun hasThickness(): Boolean? = null
}

```

### Atribuição no RecyclerView

```kotlin
    private fun setupCustomAdapterByAdapter() {
    MockData.getCustomHeader().map {
        MyExpandableAdapter(
            onSwipeOption = onCustomSwipeOption(),
            header = it,
        )
    }.let {
        ConcatAdapter.Config.Builder()
            .build().run {
                ConcatAdapter(this, it).also {
                    binding.recyclerViewExpandableAdapterDemo.adapter = it
                    binding.recyclerViewExpandableAdapterDemo.itemAnimator =
                        BaseExpandableAdapterAnimation()
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

