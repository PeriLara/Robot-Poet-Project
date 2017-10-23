package datatype.listeOrdonnee;

import java.util.ArrayList;

/**
 * implementation d'une liste ordonnée, stable par addition : add(Object), mais pas par reverse() ! Et permet les méthodes de recherche par dichotomie (en temps ln(n))
 * @param <C> type des éléments contenus dans la liste
 */
@SuppressWarnings("serial")
public class ListeOrdonnee<C extends Comparable<C>> extends ArrayList<C>{

	//---------------Méthodes d'Instanciation---------------
	
	/**
	 * 
	 */
	public ListeOrdonnee(){
		super();
	}
	
	/**
	 * @param list liste que l'on veut ordonner, complexité en n*ln(n)
	 */
	public ListeOrdonnee(ArrayList<C> list){
		this();
		for(C c : list){
			this.add(c);
		}
	}
		
	//-----------------Méthodes héritées-----------------
	
	@Override
	//en rangeant dans l'ordre croissant
    public boolean add(C elem){
    	if(this.isEmpty()) {
    		super.add(elem);
    	}
    	else{
    		int index = this.indexOf(elem);
    		if(index >= 0){
                super.add(index, elem);
    		}
    		else{
    			super.add(-(index+1), elem);
    		}
    	}
    	//afin d'implémenter add(Object)
    	return true;
    }
    
    @Override
	public C get(int i){
		return super.get(i);
	}

    @Override
    public ListeOrdonnee<C> subList(int i, int j){
    	ArrayList<C> subArray = new ArrayList<C>(super.subList(i, j));
    	ListeOrdonnee<C> subOL = new ListeOrdonnee<C>(subArray);
    	return subOL;
    }
    
    @Override
	public String toString(){
		StringBuilder sb = new StringBuilder(this.size()*10);
		for(C c : this){
			sb.append(c);
			sb.append("\n");
		}
		return sb.toString();
	}
    
	//-----------------------Méthodes-----------------------
    
	public C max(){
		return super.get(this.size()-1);
    }
    
	public C min(){
		return super.get(0);
	}
    
    /**
     * @return la liste dans l'ordre inverse
     
    public ListeOrdonnee<C> reverse(){
  	    ListeOrdonnee<C> listeRetour = new ListeOrdonnee<C>();
  	    int size = this.size();
  	    for(int i = 0; i<size; i++){
  	    	listeRetour.add(i, this.get(size-i-1));
  	    }
  	    return listeRetour;
  	}*/
    
    /**
     * cherche l'élement sepécifié. En cas de succès et retourne l'indice. En cas d'échec il retourne -|i+1|, où i est l'indice de l'élément à la place duquel il aurait du être.
  	 * @param elem element recherché dans le tableau
  	 * @return l'indice i s'il est trouvé, et sinon -|i+1|, où i est l'indice de l'élément à la place duquel il aurait du être	
  	 */
    //s'il c existe renvoie l'indice où il extise
  	public int indexOf(C elem){
  		found = false;
  		index = 0;
          int index = index(elem, this);
          return found?index:-(index+1);
    }
  	
  	//variables fonctionnelles servent à renvoyer l'inverse ssi non-trouvé
  	private boolean found;
  	private int index;
  	
  	//renvoie l'indice où il se trouve OU celui où il devrait se trouve
  	private int index(C elem, ListeOrdonnee<C> ol){
          int m = ol.size()/2;
          C milieu = ol.get(m);
          int elemCompare = elem.compareTo(milieu);
          
          //c'est lui!
      	if(elemCompare == 0){
      		found = true;
      		index = m;
          }
      	
          if(ol.size() > 1){
          	//il se trouve à gauche du pivot
  	        if(elemCompare < 0) {
  	        	index += index(elem, ol.subList(0, m));
  	        }
          	//il se trouve à droite du pivot
  	        else{
  	        	index += m +index(elem, ol.subList(m, ol.size()));
  	        }
          }
          else{
          	//il se trouve à gauche
          	if(elemCompare < 0){
          		index--;
              }
          	//il se trouve à droite
          	else{
          		index++;
          	}
          }
          return index;
  	}
 
	//-----------------------Main-----------------------
  	
    public static void main(String[] args){
        ListeOrdonnee<String> ol = new ListeOrdonnee<String>();
        ol.add("<s>");  
        ol.add(".");    
        ol.add(",");    
        ol.add("de");    
        System.out.println(ol);   
    }

}