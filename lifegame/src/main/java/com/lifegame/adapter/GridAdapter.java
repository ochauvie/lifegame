package com.lifegame.adapter;

import com.lifegame.R;
import com.lifegame.model.Cell;
import com.lifegame.model.Grid;
import com.lifegame.model.Virus;

import android.content.Context;
import android.graphics.Color;
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
        
        // Get cell by position
        int xPosition =  (int) Math.floor(position/(grid.getGridY())); // Line
        int yPosition = position - (xPosition*(grid.getGridY())); // Column
        
        Cell cell = grid.getCell(xPosition, yPosition);

        // Virus visual effect
        int backgroundColor = Color.WHITE;
        Virus virus = cell.getVirus();
        if (virus!=null) {
        	if (Virus.VIRUS_ID_A.equals(virus.getId()) || Virus.VIRUS_ID_B.equals(virus.getId())) {
        		backgroundColor = Color.BLACK;
        	} else if (Virus.VIRUS_ID_E.equals(virus.getId())) {
        		backgroundColor = Color.CYAN;
        	} else {
        		if (virus.getOwner()==1) {
        			backgroundColor = Color.GREEN;
        		} else {
        			backgroundColor = Color.MAGENTA;
        		}
        	}
        }
        imageView.setBackgroundColor(backgroundColor);
        
        // Cell status visual effect
        if (cell.getStatus()==Cell.CEL_DEAD) {
        	imageView.setImageResource(R.drawable.black);
        }
        if (cell.getStatus()==Cell.CEL_EMPTY) {
        	imageView.setImageResource(0);	
        }
        if (cell.getStatus()==Cell.CEL_IN_LIFE) {
        	
        	// Cell owner visual effect
        	if (cell.getOwner()==1) {
        		imageView.setImageResource(R.drawable.green);
        	} else if (cell.getOwner()==2) {
        		imageView.setImageResource(R.drawable.purple);
        	}
        }
        if (cell.getStatus()==Cell.CEL_NEW) {
        	imageView.setImageResource(R.drawable.red);	
        }

        return imageView;
    }


}

