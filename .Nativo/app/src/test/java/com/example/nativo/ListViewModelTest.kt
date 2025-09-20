package com.example.nativo

import android.app.Application
import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nativo.util.MainCoroutineRule
import com.example.nativo.util.getOrAwaitValue
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.ByteArrayInputStream

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ListViewModelTest {

    // Regra para executar tarefas do LiveData de forma síncrona
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Regra para usar o TestDispatcher para coroutines
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var mockApplication: Application

    @Mock
    private lateinit var mockResources: Resources

    private lateinit var viewModel: ListViewModel

    private val testItems = listOf(
        Item(1, "Apple", "A sweet red fruit", "Fruit", listOf("red", "sweet", "healthy")),
        Item(2, "Banana", "A yellow curved fruit", "Fruit", listOf("yellow", "sweet", "potassium")),
        Item(3, "Carrot", "An orange root vegetable", "Vegetable", listOf("orange", "healthy")),
        Item(4, "Apple Pie", "A delicious dessert", "Dessert", listOf("sweet", "dessert", "apple"))
    )

    @Before
    fun setup() {
        // Configurar mocks para que o ViewModel possa carregar os testItems
        `when`(mockApplication.resources).thenReturn(mockResources)
        val testJson = Gson().toJson(testItems)
        val inputStream = ByteArrayInputStream(testJson.toByteArray())
        // A IDE pode não reconhecer R.raw.item aqui, mas o teste usará o mock.
        // Usamos anyInt() porque o ID real de R.raw.item não é conhecido em tempo de compilação do teste unitário puro.
        `when`(mockResources.openRawResource(org.mockito.ArgumentMatchers.anyInt())).thenReturn(inputStream)

        viewModel = ListViewModel(mockApplication)
    }

    @Test
    fun `filterItems com termo vazio ou nulo retorna todos os itens`() {
        // Espera-se que filteredItems já tenha todos os itens após o setup e init do ViewModel
        var items = viewModel.filteredItems.getOrAwaitValue()
        assertEquals(testItems.size, items.size)
        assertEquals(testItems, items) // Verifica se todos os itens de teste estão lá

        viewModel.filterItems(null)
        items = viewModel.filteredItems.getOrAwaitValue()
        assertEquals(testItems.size, items.size)
        assertEquals(testItems, items)

        viewModel.filterItems("")
        items = viewModel.filteredItems.getOrAwaitValue()
        assertEquals(testItems.size, items.size)
        assertEquals(testItems, items)
    }

    @Test
    fun `filterItems com termo existente (case insensitive) retorna itens correspondentes`() {
        viewModel.filterItems("apple")
        val items = viewModel.filteredItems.getOrAwaitValue()
        assertEquals(2, items.size)
        assertTrue(items.any { it.name == "Apple" })
        assertTrue(items.any { it.name == "Apple Pie" })
    }

    @Test
    fun `filterItems com termo em descrição retorna itens correspondentes`() {
        viewModel.filterItems("curved")
        val items = viewModel.filteredItems.getOrAwaitValue()
        assertEquals(1, items.size)
        assertEquals("Banana", items[0].name)
    }

    @Test
    fun `filterItems com termo em tags retorna itens correspondentes`() {
        viewModel.filterItems("healthy")
        val items = viewModel.filteredItems.getOrAwaitValue()
        assertEquals(2, items.size) // Apple e Carrot
        assertTrue(items.any { it.name == "Apple" })
        assertTrue(items.any { it.name == "Carrot" })
    }

    @Test
    fun `filterItems com termo inexistente retorna lista vazia`() {
        viewModel.filterItems("nonexistentXYZ")
        val items = viewModel.filteredItems.getOrAwaitValue()
        assertEquals(0, items.size)
    }

    @Test
    fun `filterItems com termo em categoria retorna itens correspondentes`() {
        viewModel.filterItems("Fruit")
        val items = viewModel.filteredItems.getOrAwaitValue()
        assertEquals(2, items.size)
        assertTrue(items.any { it.name == "Apple" })
        assertTrue(items.any { it.name == "Banana" })
    }
}