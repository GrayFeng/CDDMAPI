package com.cdd.mapi.common.pojo;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {

	private List<T> content = new ArrayList<T>();
	private int number;
	private int size;
	private int total;

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getNumber() {
		if (number < 1) {
			number = 1;
		} else if (number > getTotalPages()) {
			number = getTotalPages();
		}
		return number;
	}

	public void setNumber(int number) {
		if(number > getTotalPages()){
			number = getTotalPages();
		}
		if(number <= 0 ){
			number = 1;
		}
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalPages() {

		return getSize() == 0 ? 0 : (int) Math.ceil((double) total
				/ (double) getSize());
	}

	public boolean hasPreviousPage() {

		return getNumber() > 1;
	}

	public boolean isFirstPage() {

		return !hasPreviousPage();
	}

	public boolean hasNextPage() {

		return (getNumber() * getSize()) < total;
	}

	public boolean isLastPage() {

		return !hasNextPage();
	}

	public Integer getNextPage() {
		if (hasNextPage()) {
			return getNumber() + 1;
		}
		return -1;
	}

	public Integer getEndPage() {
		return getTotalPages();
	}

	public Integer getPrePage() {
		if (hasPreviousPage()) {
			return getNumber() - 1;
		}
		return -1;
	}

	public Integer getStartNum() {
		return (number - 1) * size;
	}

	public Integer getEndNum() {
		int num = number * size;
		if(num > getTotal()){
			num = getTotal();
		}
		return num;
	}

}