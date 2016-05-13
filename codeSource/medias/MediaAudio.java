package medias;
import java.io.*;
import java.util.*;

public  class MediaAudio extends Media implements MediaConstant{
	  private String mArtist;
	  private String mAlbum;
	  private final int NBMETADATA=5;
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
			  String currantLine = "_" ;
		        while(!currantLine.isEmpty()){
				   currantLine = pScan.nextLine();
				   if(currantLine.trim().equals("FILE_NAME")){
					   mFileName = pScan.nextLine();
				   }else if(currantLine.trim().equals("DURATION")){
					    mDuration = Integer.valueOf(pScan.nextLine());
				   }else if(currantLine.trim().equals("YEAR")){
					   mYear = Integer.valueOf(pScan.nextLine());
				   }else if(currantLine.trim().equals("TITLE")){
					   mTitle = pScan.nextLine();
				   }else if(currantLine.trim().equals("ARTIST")){
					   mArtist = pScan.nextLine();
				   }else if(currantLine.trim().equals("ALBUM")){
					   mAlbum = pScan.nextLine();
				   }
			   }
	  }
	  
	  public String getArtist(){
		  return mArtist;
	  }
	  
	  public String getAlbum(){
		  return mAlbum;
	  }
	
	/**
	 * cette fonction permet de retourner, sous la forme
       d’un tableau de chaînes de caractères, les différents champs définissant le média. Vous
       trouverez dans l’interface MediaConstants les valeurs suivantes
	 * */
	public String[] getFullArrayInfo(){
		String[] id3container = new String[NBMETADATA]; //contient les metadonne du fichier audio
		id3container[TITLE]=mTitle;
		id3container[YEAR]=mYear+"";
		id3container[DURATION]=mDuration+"";
		id3container[ARTIST]=mArtist+"";
		id3container[ALBUM]=mAlbum+"";
		id3container[FILE_NAME]=mFileName;
		return id3container;
	}
	
	 public String toString(){
		return super.toString()+"\nARTIST\n"+mArtist+"\nALBUM\n"+mAlbum;
	}
	
	//Main de debeugage
	public static void main(String[] args) throws IOException{
		Scanner pScan=null;
			  pScan = new Scanner(new File("../libLocal.txt"));
			 if(pScan == null){
				 throw new IOException("Fichier de la library local introuvable");
			 }
			 Media ma = new MediaAudio("fichier.mp3",3,2000,"Heavy dent","Tech n9ne","unkown");	
			 Media ma2 = new MediaAudio(pScan);
			 //System.out.println(ma);
			 System.out.println(ma2);

	}
	  
}
