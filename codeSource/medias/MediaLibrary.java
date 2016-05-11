package medias;
import java.util.*;
import java.io.*;
public class MediaLibrary{
	public final int VIDE = 0;
	Vector<Media> mMediaList;
	
	public MediaLibrary(){
		mMediaList = new Vector<>(VIDE) ;
	}
	
	public MediaLibrary(Scanner pScan){
		 int typeMedia;
		 Media nouveauMedia;
		 mMediaList = new Vector<>(VIDE) ;
		 while(pScan.hasNext()){
			 typeMedia = Integer.valueOf(pScan.nextLine());
			 if(typeMedia == MediaConstant.VIDEO){
				 nouveauMedia= new MediaVideo(pScan);
				 mMediaList.add(nouveauMedia);
			 }else{
				 nouveauMedia = new MediaAudio(pScan);
				  mMediaList.add(nouveauMedia);
			 }
			 pScan.nextLine(); // lire la ligne vide
		 }
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
	
	public static void main(String[] args) throws IOException{
		Scanner pScan=null;
			 pScan = new Scanner(new File("../libLocal.txt"));
			 if(pScan == null){
				 throw new IOException("Fichier de la library local introuvable");
			 }
			 MediaLibrary ml1 = new MediaLibrary(pScan);
			  for(Media currentMedia : ml1.mMediaList){
				  System.out.println(currentMedia);
			  }
	}
}
