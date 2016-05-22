package medias;
import java.util.*;
import java.io.*;
public class MediaLibrary{
	public final int VIDE = 0;
	private Vector<Media> mMediaList;
	
	public MediaLibrary(){
		mMediaList = new Vector<>(VIDE) ;
	}
	
	public Vector<Media> getMediaList(){
		return mMediaList;
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
			
		 }
	}
	
	public void remove(int pIndex) throws ArrayIndexOutOfBoundsException{
		if(pIndex<0 || pIndex>mMediaList.size()){
			throw new ArrayIndexOutOfBoundsException("Le media a supprimer n'existe pas");
		}
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
	
	public String[] getBasicFieldInfo(){
		String[] basicField = {"Titre","Annee","Duree"};
		
		return basicField;
	}
	
	public String[] getAudioFiledInfo(){
		String[] audioField = {"Titre","Annee","Duree","Artiste","Album","ficher"};
		return audioField;
	}
	
	public String[] getVideoFieldInfo(){
		String[] videoField = {"Titre","Annee","Duree","Frequance de trame","Dimention","ficher"};
		return videoField;
	}
	
	public String[][] getBasicLibraryInfo(){
		 String[][] basicLibraryInfo = new String[mMediaList.size()][3];
		 
		for(int i=0;i<mMediaList.size();i++){
			basicLibraryInfo[i][MediaConstant.TITLE] = mMediaList.get(i).getTitle();
			basicLibraryInfo[i][MediaConstant.YEAR] = mMediaList.get(i).getYear()+"";
			basicLibraryInfo[i][MediaConstant.DURATION] = mMediaList.get(i).getDuration()+"";
			
		}
		return basicLibraryInfo;
	}
	
	public String[] getFullMediaInfo(int pIdx){
		String[] fullMediaInfo = new String[6];
		fullMediaInfo[MediaConstant.TITLE]=mMediaList.get(pIdx).getTitle();
		fullMediaInfo[MediaConstant.YEAR]=mMediaList.get(pIdx).getYear()+"";
		fullMediaInfo[MediaConstant.DURATION]=mMediaList.get(pIdx).getDuration()+"";
		if(mMediaList.get(pIdx) instanceof MediaVideo){
			MediaVideo mediaV = (MediaVideo)mMediaList.get(pIdx);
			fullMediaInfo[MediaConstant.FRAME_RATE] = mediaV.getFrameRate()+"";
			fullMediaInfo[MediaConstant.DIMENSION] = mediaV.getWidth()+"X"+mediaV.getHeight();
			
		}else if(mMediaList.get(pIdx) instanceof MediaAudio){
			MediaAudio mediaA = (MediaAudio)mMediaList.get(pIdx);
			fullMediaInfo[MediaConstant.ARTIST] = mediaA.getArtist();
			fullMediaInfo[MediaConstant.ALBUM] = mediaA.getAlbum();
		}
		fullMediaInfo[MediaConstant.FILE_NAME] = mMediaList.get(pIdx).getFileName();
		return fullMediaInfo;
	}
	
	public static void main(String[] args) throws IOException{
		Scanner pScan=null;
			 pScan = new Scanner(new File("../libLocal.txt"));
			 if(pScan == null){
				 throw new IOException("Fichier de la library local introuvable");
			 }
			 MediaLibrary ml1 = new MediaLibrary(pScan);
			 System.out.println("Taille vecteur "+ ml1.mMediaList.size());
			  for(Media currentMedia : ml1.mMediaList){
				  System.out.println(currentMedia);
			  }
			  
			   String[][] info = ml1.getBasicLibraryInfo();
			  for(int i=0;i<info.length;i++){
				  for(int j=0;j<info[i].length;j++){
					  System.out.println("info[i][j] "+info[i][j]);
				  }
			  }
			  
	}
}
