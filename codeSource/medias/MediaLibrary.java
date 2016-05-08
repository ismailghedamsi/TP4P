import java.util.*;
public class MediaLibrary{
	public final int VIDE = 0;
	Vector<Media> mMediaList;
	
	public MediaLibrary(){
		mMediaList = new Vector<>(VIDE) ;
	}
	
	public void remove(int pIndex){
		mMediaList.remove(pIndex);
	}
	
	public void add(Media pMedia){
		mMediaList.addElement(pMedia);
	}
	
	public int size(){
		return mMediaList.size();
	}
	
	public Media get(int pIdx){
		return mMediaList.get(pIdx);
	}
}
