package com.example.shareroutine.ui.adapter

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shareroutine.R
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.databinding.RoutineDetailListChildBinding
import com.example.shareroutine.databinding.RoutineDetailListParentBinding
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class ExpandableRoutineAdapter(
    private val routine: Routine,
    private val onLongClickListener: View.OnLongClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
       private const val VIEW_TYPE_CHILD = 0
       private const val VIEW_TYPE_PARENT = 1

       private const val IC_EXPANDED_ROTATION_DEG = 180F
       private const val IC_COLLAPSED_ROTATION_DEG = 0F
    }

    class ParentViewHolder(
        val binding: RoutineDetailListParentBinding,
        onClickListener: View.OnClickListener,
        onLongClickListener: View.OnLongClickListener?
        ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(onClickListener)
            binding.root.setOnLongClickListener(onLongClickListener)
        }
    }
    class ChildViewHolder(val binding: RoutineDetailListChildBinding) : RecyclerView.ViewHolder(binding.root)

    private var isExpanded: Boolean by Delegates.observable(false) { _: KProperty<*>, _: Boolean, newExpandedValue: Boolean ->
        if (newExpandedValue) {
            notifyItemRangeInserted(1, routine.todos.size)
            //To update the header expand icon
            notifyItemChanged(0)
        } else {
            notifyItemRangeRemoved(1, routine.todos.size)
            //To update the header expand icon
            notifyItemChanged(0)
        }
    }

    private val onHeaderClickListener = View.OnClickListener {
        isExpanded = !isExpanded
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_PARENT -> {
                val view = layoutInflater.inflate(R.layout.routine_detail_list_parent, parent, false)
                ParentViewHolder(
                    RoutineDetailListParentBinding.bind(view),
                    onHeaderClickListener,
                    onLongClickListener
                )
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.routine_detail_list_child, parent, false)
                ChildViewHolder(RoutineDetailListChildBinding.bind(view))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ParentViewHolder -> {
                holder.binding.routineDetailTitle.text = routine.name
                holder.binding.routineDetailListArrow.rotation = if (isExpanded) IC_EXPANDED_ROTATION_DEG else IC_COLLAPSED_ROTATION_DEG

                if (routine.isUsed) {
                    val spannable = SpannableString(routine.name)
                    spannable.setSpan(UnderlineSpan(), 0, spannable.length, 0)
                    holder.binding.routineDetailTitle.text = spannable
                    holder.binding.routineDetailTitle.setTextColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            R.color.share_routine_theme_dark_purple
                        )
                    )
                }
            }
            is ChildViewHolder -> {
                holder.binding.routineDetailDescription.text = routine.todos[position - 1].description
            }
        }
    }

    override fun getItemCount(): Int = if (isExpanded) routine.todos.size + 1 else 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_PARENT else VIEW_TYPE_CHILD
    }
}