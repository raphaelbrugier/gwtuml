package com.objetdirect.gwt.umlapi.client.webinterface;

/**
 * This enumeration lists the available quality level for the drawer
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public enum QualityLevel {
	
	 /**
	 * Highest quality level requires JIT JavaScript Compile
	 */
	VERY_HIGH("Very High", "Slow", 40),
	 /**
	 * It is the best compromise quality level available for medium to fast PCs/browsers
	 */
	HIGH("High", "For good pc and browser", 30), 
	 /**
	 * It is the normal quality, worths a try if High is slow
	 */
	NORMAL("Normal", "Recommended for real browser", 20),
	 /**
	 * Lowest quality but not so fast by the way
	 */
	LOW("Low", "For very old pc and IE users", 10);


	/**
	 * Compare two quality level and return true if the first is higher or equal than the second
	 * 
	 * @param q1 The first quality level
	 * @param q2 The first quality level
	 * @return True if the first quality level is higher or equal than the second
	 */
	public static boolean compare(final QualityLevel q1,
		final QualityLevel q2) {
	    return q1.index >= q2.index;
	}
	/**
	 * Return a the quality level that corresponds to the quality index
	 * 
	 * @param qualityIndex the integer for the quality to retrieve
	 * @return The {@link QualityLevel} corresponding to the index
	 */
	public static QualityLevel getQualityFromIndex(final int qualityIndex) {
	    for (final QualityLevel qlvl : QualityLevel.values()) {
		if (qlvl.index == qualityIndex) {
		    return qlvl;
		}
	    }
	    return QualityLevel.HIGH;
	}
	/**
	 * Return a the quality level that corresponds to the String name
	 * 
	 * @param qualityName the String for the quality to retrieve
	 * @return The {@link QualityLevel} corresponding to the name
	 */
	public static QualityLevel getQualityFromName(final String qualityName) {
	    for (final QualityLevel qlvl : QualityLevel.values()) {
		if (qlvl.toString().equalsIgnoreCase(qualityName)) {
		    return qlvl;
		}
	    }
	    return QualityLevel.HIGH;
	}
	
	    /**
	     * Return true if the current quality level is almost the given level
	     *
	     * @param level The level to compare with
	     *
	     * @return True if the current quality level is almost the given level, False otherwise
	     */
	    public static boolean IsAlmost(final QualityLevel level) {
	        return OptionsManager.get("QualityLevel") >= level.index; 
	    }



	private final String description;

	private final int index;

	private final String name;

	private QualityLevel(final String name, final String description,
		final int index) {
	    this.name = name;
	    this.description = description;
	    this.index = index;

	}

	/**
	 * Getter for the {@link QualityLevel} description 
	 * 
	 * @return the description
	 */
	public String getDescription() {
	    return this.description;
	}

	/** 
	 * Getter for the {@link QualityLevel} name
	 *  
	 * @return the name
	 */
	public String getName() {
	    return this.name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
	    return this.name + " (" + this.description + ")";
	}
}