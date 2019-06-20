package entities;

public class Event {

	int id;
	String date,grp,batch,startTime,endTime,activity,description,venue;
	
	public Event() {
		
	}
	
	public Event(String date, String grp, String batch, String startTime, String endTime, String activity, String venue) {
		super();
		this.date = date;
		this.grp = grp;
		this.batch = batch;
		this.startTime = startTime;
		this.endTime = endTime;
		this.activity = activity;
		this.venue = venue;
	}

	public Event(String date, String grp, String batch, String startTime, String endTime, String activity, String description,
			String venue) {
		super();
		this.date = date;
		this.grp = grp;
		this.batch = batch;
		this.startTime = startTime;
		this.endTime = endTime;
		this.activity = activity;
		this.description = description;
		this.venue = venue;
	}
	
	public Event(int id, String date, String grp, String batch, String startTime, String endTime, String activity, String description,
			String venue) {
		super();
		this.id = id;
		this.date = date;
		this.grp = grp;
		this.batch = batch;
		this.startTime = startTime;
		this.endTime = endTime;
		this.activity = activity;
		this.description = description;
		this.venue = venue;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getGrp() {
		return grp;
	}
	public void setGrp(String grp) {
		this.grp = grp;
	}
	
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	
	
}
