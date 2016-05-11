package medias;
import java.io.*;
import java.util.*;

public  class MediaAudio extends Media implements MediaConstant{
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
           //int typeMedia;
		      // typeMedia = Integer.valueOf(pScan.nextLine());
			   pScan.nextLine();
			   mFileName = pScan.nextLine();
			   pScan.nextLine();
			   mDuration = Integer.valueOf(pScan.nextLine());
			   pScan.nextLine();
			   mYear = Integer.valueOf(pScan.nextLine());
			   pScan.nextLine();
			   mTitle = pScan.nextLine();
			   pScan.nextLine();
			   mArtist = pScan.nextLine();
			   pScan.nextLine();
			   mAlbum = pScan.nextLine();
			   
	  }
	
	public String[] getFullArrayInfo(){
		return null;
	}
	
	@Override
	 public String toString(){
		return super.toString()+"\nARTIST\n"+mArtist+"\nALBUM\n"+mAlbum;
	}
	
	//Main de debeugage
	/*public static void main(String[] args) throws IOException{
		Scanner pScan=null;
			  pScan = new Scanner(new File("../libLocal.txt"));
			 if(pScan == null){
				 throw new IOException("Fichier de la library local introuvable");
			 }
			 Media ma = new MediaAudio("fichier.mp3",3,2000,"Heavy dent","Tech n9ne","unkown");	
			 Media ma2 = new MediaAudio(pScan);
			 //System.out.println(ma);
			 System.out.println(ma2);

	}*/
	  
}
