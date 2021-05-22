package pt.unl.fct.di.apdc.helpinhand.api;

public class RoutesData {

	private String title;
	private String district;
	private String description;
	private String start;
	private String finish;
	private String marker1dsc;
	private String coordMarker1;
	private String marker2dsc;
	private String coordMarker2;
	private String marker3dsc;
	private String coordMarker3;
	private String waypoints;
	
	public RoutesData() {
		
	}
	
	
	public RoutesData(String title, String district, String description,
			String start, String finish, String marker1dsc, String coordMarker1,
			String marker2dsc, String coordMarker2, String marker3dsc, 
			String coordMarker3, String waypoints) {
		this.title=title;
		this.district=district;
		this.description = description;
		this.start = start;
		this.finish = finish;
		this.marker1dsc = marker1dsc;
		this.coordMarker1 = coordMarker1;
		this.marker2dsc = marker2dsc;
		this.coordMarker2 = coordMarker2;
		this.marker3dsc = marker3dsc;
		this.coordMarker3 = coordMarker3;
		this.waypoints=waypoints;
		
		
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public String getFinish() {
		return finish;
	}


	public void setFinish(String finish) {
		this.finish = finish;
	}


	public String getMarker1dsc() {
		return marker1dsc;
	}


	public void setMarker1dsc(String marker1dsc) {
		this.marker1dsc = marker1dsc;
	}


	public String getCoordMarker1() {
		return coordMarker1;
	}


	public void setCoordMarker1(String coordMarker1) {
		this.coordMarker1 = coordMarker1;
	}


	public String getMarker2dsc() {
		return marker2dsc;
	}


	public void setMarker2dsc(String marker2dsc) {
		this.marker2dsc = marker2dsc;
	}


	public String getCoordMarker2() {
		return coordMarker2;
	}


	public void setCoordMarker2(String coordMarker2) {
		this.coordMarker2 = coordMarker2;
	}


	public String getMarker3dsc() {
		return marker3dsc;
	}


	public void setMarker3dsc(String marker3dsc) {
		this.marker3dsc = marker3dsc;
	}


	public String getCoordMarker3() {
		return coordMarker3;
	}


	public void setCoordMarker3(String coordMarker3) {
		this.coordMarker3 = coordMarker3;
	}


	public String getWaypoints() {
		return waypoints;
	}


	public void setWaypoints(String waypoints) {
		this.waypoints = waypoints;
	}
	
	
}
