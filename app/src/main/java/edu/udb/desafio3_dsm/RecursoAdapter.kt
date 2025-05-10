import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.udb.desafio3_dsm.DBHelper
import edu.udb.desafio3_dsm.R
import edu.udb.desafio3_dsm.Recurso

class RecursoAdapter(
    private val recursos: List<Recurso>,
    private val db: DBHelper,
    private val onItemClick: (Recurso) -> Unit
) : RecyclerView.Adapter<RecursoAdapter.RecursoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecursoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recursos, parent, false)
        return RecursoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecursoViewHolder, position: Int) {
        val recurso = recursos[position]
        holder.bind(recurso)
    }

    override fun getItemCount(): Int = recursos.size

    inner class RecursoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvId: TextView = itemView.findViewById(R.id.tvId)
        private val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        private val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        private val tvEnlace: TextView = itemView.findViewById(R.id.tvEnlace)
        private val tvImagen: TextView = itemView.findViewById(R.id.tvImagen)

        // Si decides mostrar imagen directamente:
        // private val imgPreview: ImageView = itemView.findViewById(R.id.imgPreview)

        fun bind(recurso: Recurso) {
            tvId.text = "ID: ${recurso.id}"
            tvTitulo.text = recurso.titulo
            tvDescripcion.text = recurso.descripcion
            tvEnlace.text = recurso.enlace
            tvImagen.text = recurso.imagen

            tvEnlace.setOnClickListener {
                abrirEnlace(recurso.enlace)
            }

            tvImagen.setOnClickListener {
                abrirEnlace(recurso.imagen)
            }


        }

        private fun abrirEnlace(url: String) {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                itemView.context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(itemView.context, "No se puede abrir el enlace", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
