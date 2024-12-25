package yeong.wish.lotto.menuFragmentPage.FateFP2;


import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Num645Adapter extends RecyclerView.Adapter<Num645Adapter.viewHolder> {
    ArrayList<Number_lotto> items = new ArrayList<>();

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.ball_statistics, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Number_lotto item = items.get(position);
        holder.setItem(item);
        holder.setProgressBarColor(item.getId());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Number_lotto item) {
        items.add(item);
    }

    public void setItems(ArrayList<Number_lotto> items) {
        this.items = items;
    }

    public Number_lotto getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Number_lotto item) {
        items.set(position, item);
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        static int maxlength;
        ImageView imageView;
        TextView textView;
        ProgressBar progressBar;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView2);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        public void setItem(Number_lotto item) {
            imageView.setImageBitmap(item.getBitmap());
            textView.setText(String.valueOf(item.number_length));
            progressBar.setProgress(item.number_length);
            progressBar.setMax(maxlength);
        }

        public void setProgressBarColor(int number) {
            int color = getColorForNumber(number);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                progressBar.setProgressTintList(ColorStateList.valueOf(color));
            } else {
                Drawable progressDrawable = progressBar.getProgressDrawable().mutate();
                progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                progressBar.setProgressDrawable(progressDrawable);
            }
        }

        private int getColorForNumber(int number) {
            if (1 <= number && number <= 10) {
                return Color.YELLOW;
            } else if (11 <= number && number <= 20) {
                return Color.BLUE;
            } else if (21 <= number && number <= 30) {
                return Color.RED;
            } else if (31 <= number && number <= 40) {
                return Color.GRAY;
            } else if (41 <= number && number <= 45) {
                return Color.GREEN;
            }
            return Color.BLACK; // 기본값
        }
    }
}
