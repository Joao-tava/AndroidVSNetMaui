namespace App_Paridade.Models;

public class Item
{
    public int Id { get; set; }           // Identificador único
    public string Title { get; set; }     // Título principal (será mostrado na lista)
    public string Description { get; set; } // Descrição detalhada (usada na página de detalhe)
}
