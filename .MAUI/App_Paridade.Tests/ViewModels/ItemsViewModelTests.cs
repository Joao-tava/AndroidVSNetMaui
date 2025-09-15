using System.Collections.Generic;
using System.Threading.Tasks;
using Xunit;
using App_Paridade.ViewModels;
using App_Paridade.Services;
using App_Paridade.Models;

namespace App_Paridade.Tests
{
    // Fake do IDataService para não depender de JSON real
    public class FakeDataService : IDataService
    {
        public Task<List<Item>> GetItemsAsync()
        {
            var items = new List<Item>
            {
                new Item { Title = "Banana", Description = "Fruta amarela" },
                new Item { Title = "Maçã", Description = "Fruta vermelha" },
                new Item { Title = "Laranja", Description = "Fruta cítrica" }
            };


            return Task.FromResult(items);
        }
    }

    public class ItemsViewModelTests
    {
        [Fact]
        public async Task SearchText_FiltraListaCorretamente()
        {
            // Arrange
            var fakeService = new FakeDataService();
            var viewModel = new ItemsViewModel(fakeService);

            await viewModel.LoadItemsAsync();

            // Act
            viewModel.SearchText = "ma"; // deve pegar "Maçã", mas não "Banana"

            // Assert
            Assert.Contains(viewModel.Items, i => i.Title == "Maçã");
            Assert.DoesNotContain(viewModel.Items, i => i.Title == "Banana");
        }
    }
}
