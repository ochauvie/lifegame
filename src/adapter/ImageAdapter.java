package adapter;

import com.lifegame.R;
import com.lifegame.model.Cell;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private int[][] grid;
	private int gridX;
	private int gridY;
	private Context mContext;

    public ImageAdapter(Context c, int[][] grid, int gridX, int gridY) {
        mContext = c;
        this.grid = grid;
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public int getCount() {
    	return (gridX)*(gridY); 
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(15, 15));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }
        
        int xPosition =  (int) Math.floor(position/(gridY));
        int yPosition = position - (xPosition*(gridY));
               
        if (grid[xPosition][yPosition]==Cell.CEL_DEAD) {
        	imageView.setImageResource(R.drawable.black);	
        }
        if (grid[xPosition][yPosition]==Cell.CEL_EMPTY) {
        	imageView.setImageResource(0);	
        }
        if (grid[xPosition][yPosition]==Cell.CEL_IN_LIFE) {
        	imageView.setImageResource(R.drawable.green);	
        }
        if (grid[xPosition][yPosition]==Cell.CEL_NEW) {
        	imageView.setImageResource(R.drawable.red);	
        }

        return imageView;
    }


}

