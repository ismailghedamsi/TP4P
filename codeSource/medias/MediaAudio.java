import java.io.*;
import java.util.*;
public  class MediaAudio extends Media{
	  private int MP3=1;
	  private int MP4=0;
	  private String mArtist;
	  private String mAlbum;
	  
	  public MediaAudio(String pFileName,int pDuration,int pYear,String pTitle,String pArtist,String pAlbum){
		  super(pFileName,pDuration,pYear,pTitle);
		  mArtist = pArtist;
		  mAlbum = pAlbum;
	  }
	  
	  public MediaAudio(MediaAudio pMedia){
		  super(pMedia);
		  this.mArtist = pMedia.mArtist;
		  this.mAlbum = pMedia.mAlbum;
	  }
	  
	  public MediaAudio(Scanner pScan){

		int mediaType=-1;
		String filePath;
	    mediaType = Integer.valueOf(pScan.next());
		System.out.println("mediaType"+mediaType);
		if(mediaType == MP3){
			System.out.println(pScan.next());
			filePath = pScan.next();
			System.out.println(filePath);
	    }
			
	}
	
	public String[] getFullArrayInfo(){
		return null;
	}
	
	@Override
	 public String toString(){
		return mArtist+" "+mAlbum+"";
	}
	
	public static void main(String[] args) throws IOException{
		Scanner pScan=null;
			 pScan = new Scanner(new File("libLocal.txt"));
			 if(pScan == null){
				 throw new IOException("Fichier de la library local introuvablr");
			 }
			 MediaAudio ma = new MediaAudio(pScan);	
			 System.out.println(ma);

	}
	  
}
