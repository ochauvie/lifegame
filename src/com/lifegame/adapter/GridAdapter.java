package com.lifegame.adapter;

import com.lifegame.R;
import com.lifegame.model.Cell;
import com.lifegame.model.Grid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter {

	private Grid grid;
	private Context mContext;

    public GridAdapter(Context c, Grid grid) {
        mContext = c;
        this.grid = grid;
    }

    public int getCount() {
    	return (grid.getGridX() * grid.getGridY()); 
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
        
        int xPosition =  (int) Math.floor(position/(grid.getGridY()));
        int yPosition = position - (xPosition*(grid.getGridY()));
        
        Cell cell = grid.getCell(xPosition, yPosition);
        
        if (cell.getStatus()==Cell.CEL_DEAD) {
        	imageView.setImageResource(R.drawable.black);	
        }
        if (cell.getStatus()==Cell.CEL_EMPTY) {
        	imageView.setImageResource(0);	
        }
        if (cell.getStatus()==Cell.CEL_IN_LIFE) {
        	imageView.setImageResource(R.drawable.green);	
        }
        if (cell.getStatus()==Cell.CEL_NEW) {
        	imageView.setImageResource(R.drawable.red);	
        }

        return imageView;
    }


}

