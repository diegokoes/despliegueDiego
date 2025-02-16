package es.daw.jakarta.models;

import java.util.List;
import java.util.ArrayList;

public class Carro {
    private List<ItemCarro> items;
    private final double DESCUENTO = 0.75;

    public Carro() {
        items = new ArrayList<>();
    }

    public List<ItemCarro> getItems() {
        return items;
    }

    public double getDESCUENTO() {
        return DESCUENTO;
    }

    public void addItemCarro(ItemCarro itemCarro) {
        if (items.contains(itemCarro)) {
            int pos = items.indexOf(itemCarro);
            ItemCarro item = items.get(pos);
            item.setCantidad(item.getCantidad() + 1);
        } else {
            items.add(itemCarro);
        }
    }

    public boolean aplicarDescuento() {
        return items.size() > 2;
    }

    public double getTotal() {
        if (!aplicarDescuento()) {
            return items.stream().mapToInt(ItemCarro::getImporte).sum();
        } else {
            return items.stream().mapToInt(ItemCarro::getImporte).sum() * DESCUENTO;
        }
    }

    public void actualizarCantidadItem(Long productId, int cantidad) {
        for (ItemCarro item : items) {
            if (item.getProducto().getId().equals(productId)) {
                item.setCantidad(cantidad);
                break;
            }
        }
    }

    public void eliminarItem(Long productId) {
        items.removeIf(item -> item.getProducto().getId().equals(productId));
    }
}
