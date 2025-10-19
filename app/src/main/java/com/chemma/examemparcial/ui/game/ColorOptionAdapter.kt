package com.chemma.examemparcial.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chemma.examemparcial.databinding.ItemColorOptionBinding

/**
 * Adaptador para el RecyclerView que muestra las opciones de colores en el juego.
 *
 * @param colorOptions La lista de IDs de recursos de color para mostrar.
 * @param onColorSelected Una función lambda que se ejecuta cuando el usuario selecciona un color.
 */
class ColorOptionAdapter(
    private val colorOptions: List<Int>,
    private val onColorSelected: (Int) -> Unit
) : RecyclerView.Adapter<ColorOptionAdapter.ColorViewHolder>() {

    /**
     * Crea una nueva vista (ViewHolder) para cada elemento de la lista.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val binding = ItemColorOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorViewHolder(binding)
    }

    /**
     * Vincula los datos (el color) a una vista (ViewHolder) específica.
     */
    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colorOptions[position])
    }

    /**
     * Devuelve el número total de elementos en la lista.
     */
    override fun getItemCount() = colorOptions.size

    /**
     * Clase interna que representa la vista de un solo elemento (un color) en la lista.
     */
    inner class ColorViewHolder(private val binding: ItemColorOptionBinding) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Asigna el color de fondo y el listener de clic a la vista del elemento.
         */
        fun bind(color: Int) {
            binding.root.setCardBackgroundColor(itemView.context.getColor(color))
            binding.root.setOnClickListener { onColorSelected(color) }
        }
    }
}
