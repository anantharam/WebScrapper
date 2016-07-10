package webparser;

import java.util.List;

public class CompanyModel {
	
	private String name;
	private String moreInfoLink;
	private List<String> phone;
	private String address;
	private String landmark;
	private String pincode;
	private String rating;
	private String ratingValue;
	private String year;
	private String hoursOfOperation;
	private List<String> tags;
	private List<String> photos;
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the phone
	 */
	public List<String> getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(List<String> phone) {
		this.phone = phone;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}
	/**
	 * @return the ratingValue
	 */
	public String getRatingValue() {
		return ratingValue;
	}
	/**
	 * @param ratingValue the ratingValue to set
	 */
	public void setRatingValue(String ratingValue) {
		this.ratingValue = ratingValue;
	}
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the hoursOfOperation
	 */
	public String getHoursOfOperation() {
		return hoursOfOperation;
	}
	/**
	 * @param hoursOfOperation the hoursOfOperation to set
	 */
	public void setHoursOfOperation(String hoursOfOperation) {
		this.hoursOfOperation = hoursOfOperation;
	}
	/**
	 * @return the justDialLink
	 */
	public String getMoreInfoLink() {
		return moreInfoLink;
	}
	/**
	 * @param justDialLink the justDialLink to set
	 */
	public void setMoreInfoLink(String justDialLink) {
		this.moreInfoLink = justDialLink;
	}
	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	/**
	 * @return the photos
	 */
	public List<String> getPhotos() {
		return photos;
	}
	
	/**
	 * @param photos the photos to set
	 */
	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}
	
	/**
	 * @return the landmark
	 */
	public String getLandmark() {
		return landmark;
	}
	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}
	/**
	 * @param landmark the landmark to set
	 */
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
}
