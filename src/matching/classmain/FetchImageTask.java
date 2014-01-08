package matching.classmain;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class FetchImageTask extends AsyncTask<String, Void, Bitmap>{

	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		String url=params[0];
		Bitmap bitmap = null;
		try {
			bitmap=BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * @param args
	 */
	

}
