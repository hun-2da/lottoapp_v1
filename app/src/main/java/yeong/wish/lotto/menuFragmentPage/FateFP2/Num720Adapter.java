package yeong.wish.lotto.menuFragmentPage.FateFP2;



public class Num720Adapter extends RecyclerView.Adapter<Num720Adapter.viewHolder720> {
    ArrayList<Number_lotto> items = new ArrayList<>();

    @NonNull
    @Override
    public viewHolder720 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.ball_statistics, parent, false);
        return new viewHolder720(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Num720Adapter.viewHolder720 holder, int position) {
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

    static class viewHolder720 extends RecyclerView.ViewHolder {
        static int maxlength;
        ImageView imageView;
        TextView textView;
        ProgressBar progressBar;
        Resources resources;

        public viewHolder720(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView2);
            progressBar = itemView.findViewById(R.id.progressBar);
            resources = itemView.getResources();
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
            if (0 <= number && number <= 4) {
                return Color.LTGRAY;
            } else if (5 <= number && number <= 14) {
                return resources.getColor(R.color.lred);
            } else if (15 <= number && number <= 24) {
                return resources.getColor(R.color.orange);
            } else if (25 <= number && number <= 34) {
                return Color.YELLOW;
            } else if (35 <= number && number <= 44) {
                return resources.getColor(R.color.lblue);
            } else if (45 <= number && number <= 54) {
                return resources.getColor(R.color.purple);
            }else if (55 <= number && number <= 64) {
                return Color.DKGRAY;
            }else
                return Color.BLACK; // 기본값
        }
    }
}
