package cs310;

//  Figure out how to recode the qualifying exam problem using a Map for each terminal line

//  "java cs310.TermReport<data.txt"

import java.util.HashMap;

public class LineUsageData {
    //private SinglyLinkedList<Usage> data;
    private HashMap<Integer, Usage> data; // Order not important, so we use a HashMap.

    public LineUsageData() {
        data = new HashMap<Integer, Usage>();
    }

    // add one sighting of a use on this line
    public void addObservation(String username) {
        Usage user;
        boolean found = false;

        // Go through linked list, searching for a specific user
        // Once found, alter users count through Usage API
        // and break out of while loop.
        int i;
        for (i = 0; i < data.size(); i++) {
            user = data.get(i);
            if (user.getUser().equals(username)) {
                user.setCount(user.getCount() + 1);
                found = true;
            }
            if (found)
                break;
        }
        // If we never found that user in the loop, create and add one
        if (!found) {
            user = new Usage(username);
            data.put(i, user);
        }
    }

    // find the user with the most sightings on this line
    public Usage findMaxUsage() {
        Usage user;
        Usage record = new Usage("<NONE>");
        record.setCount(0); // init running max
        // Go through linked list and there is a new high login
        // count found, update record.
        for (int i = 0; i < data.size(); i++) {
            user = data.get(i);
            if (user.getCount() > record.getCount()) {
                record = user;
            }
        }
        return record;
    }
}
