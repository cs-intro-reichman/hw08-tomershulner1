/** Represnts a list of musical tracks. The list has a maximum capacity (int),
 *  and an actual size (number of tracks in the list, an int). */
class PlayList {
    private Track[] tracks;  // Array of tracks (Track objects)   
    private int maxSize;     // Maximum number of tracks in the array
    private int size;        // Actual number of tracks in the array

    /** Constructs an empty play list with a maximum number of tracks. */ 
    public PlayList(int maxSize) {
        this.maxSize = maxSize;
        tracks = new Track[maxSize];
        size = 0;
    }

    /** Returns the maximum size of this play list. */ 
    public int getMaxSize() {
        return maxSize;
    }
    
    /** Returns the current number of tracks in this play list. */ 
    public int getSize() {
        return size;
    }

    /** Method to get a track by index */
    public Track getTrack(int index) {
        if (index >= 0 && index < size) {
            return tracks[index];
        } else {
            return null;
        }
    }
    
    /** Appends the given track to the end of this list. 
     *  If the list is full, does nothing and returns false.
     *  Otherwise, appends the track and returns true. */
    public boolean add(Track track) {
        if (size == maxSize) {
            return false;
        }
        tracks[size] = track;
        size += 1;
        return true;
    }

    /** Returns the data of this list, as a string. Each track appears in a separate line. */
    //// For an efficient implementation, use StringBuilder.
    public String toString() {
        StringBuilder res = new StringBuilder("");
        for (int i = 0; i < size; i++) {
            res.append(tracks[i].toString() + "\n");
        }
        return res.toString();
    }

    /** Removes the last track from this list. If the list is empty, does nothing. */
     public void removeLast() {
        if(size == 0) {
            return;
        }
        tracks[size - 1] = null;
        size -= 1;
    }
    
    /** Returns the total duration (in seconds) of all the tracks in this list.*/
    public int totalDuration() {
        int total_duration = 0;
        for(int i = 0; i < size; i++) {
            total_duration += tracks[i].getDuration();
        }
        return total_duration;
    }

    /** Returns the index of the track with the given title in this list.
     *  If such a track is not found, returns -1. */
    public int indexOf(String title) {
        for(int i = 0; i < size; i++) {
            if (tracks[i].getTitle().toLowerCase().equals(title.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    /** Inserts the given track in index i of this list. For example, if the list is
     *  (t5, t3, t1), then just after add(1,t4) the list becomes (t5, t4, t3, t1).
     *  If the list is the empty list (), then just after add(0,t3) it becomes (t3).
     *  If i is negative or greater than the size of this list, or if the list
     *  is full, does nothing and returns false. Otherwise, inserts the track and
     *  returns true. */
    public boolean add(int index, Track track) {
        if (index < 0 || index > size || size == maxSize) {
            return false;
        }
        if(size == 0) {
            tracks[index] = track;
            return true;
        }
        // Move every track to the next spot in the playlist
        for(int i = index; i < size - 1; i++) {
            tracks[i + 1] = tracks[i];
        }
        tracks[index] = track; // Insert the new track 
        size += 1;
        return true;
    }
     
    /** Removes the track in the given index from this list.
     *  If the list is empty, or the given index is negative or too big for this list, 
     *  does nothing and returns -1. */
    public void remove(int index) {
        if(size == 0 || index < 0 || index > maxSize) {
            return;
        }
        tracks[index] = null;
        for (int i = index + 1; i < size - 1; i++) { 
            tracks[i - 1] = tracks[i];
        }
        tracks[size] = null;
        size -= 1;
    }

    /** Removes the first track that has the given title from this list.
     *  If such a track is not found, or the list is empty, or the given index
     *  is negative or too big for this list, does nothing. */
    public void remove(String title) {
       remove(indexOf(title));
    }

    /** Removes the first track from this list. If the list is empty, does nothing. */
    public void removeFirst() {
        for(int i = 0; i < size - 1; i++) {
            tracks[i] = tracks[i + 1];
        }
        tracks[size - 1] = null;
    }
    
    /** Adds all the tracks in the other list to the end of this list. 
     *  If the total size of both lists is too large, does nothing. */
     public void add(PlayList other) {
        if(size + other.getSize() > maxSize) {
            return;
        }
        for (int i = 0; i < other.getSize(); i++) {
            add(other.getTrack(i));
        }
    }

    /** Returns the index in this list of the track that has the shortest duration,
     *  starting the search in location start. For example, if the durations are 
     *  7, 1, 6, 7, 5, 8, 7, then min(2) returns 4, since this the index of the 
     *  minimum value (5) when starting the search from index 2.  
     *  If start is negative or greater than size - 1, returns -1.
     */
    private int minIndex(int start) {
        if (start < 0 || start >= size) {
            return -1;
        }
        int min_index = start;
        int min_duration = tracks[start].getDuration();
        for(int i = start; i < size; i++) {
            int current_duration = tracks[i].getDuration();
            if (current_duration < min_duration) {
                min_index = i;
                min_duration = current_duration;
            }
        }
        return min_index;
    }

    /** Returns the title of the shortest track in this list. 
     *  If the list is empty, returns null. */
    public String titleOfShortestTrack() {
        if (size == 0){
            return null;
        }
        return tracks[minIndex(0)].getTitle();
    }

    /** Sorts this list by increasing duration order: Tracks with shorter
     *  durations will appear first. The sort is done in-place. In other words,
     *  rather than returning a new, sorted playlist, the method sorts
     *  the list on which it was called (this list). */
    public void sortedInPlace() {
        for (int i = 0; i < size - 1; i++) {
            int current_min = minIndex(i);
            Track first_track = tracks[i];
            // make the swap
            tracks[i] = getTrack(current_min);
            tracks[current_min] = first_track;
        }
    }
}
