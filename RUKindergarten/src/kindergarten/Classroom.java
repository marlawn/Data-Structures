package kindergarten;

/**
 * This class represents a Classroom, with:
 * - an SNode instance variable for students in line,
 * - an SNode instance variable for musical chairs, pointing to the last student
 * in the list,
 * - a boolean array for seating availability (eg. can a student sit in a given
 * seat), and
 * - a Student array parallel to seatingAvailability to show students filed into
 * seats
 * --- (more formally, seatingAvailability[i][j] also refers to the same seat in
 * studentsSitting[i][j])
 * 
 * @author Ethan Chou
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 */
public class Classroom {
    private SNode studentsInLine; // when students are in line: references the FIRST student in the LL
    private SNode musicalChairs; // when students are in musical chairs: references the LAST student in the CLL
    private boolean[][] seatingAvailability; // represents the classroom seats that are available to students
    private Student[][] studentsSitting; // when students are sitting in the classroom: contains the students

    /**
     * Constructor for classrooms. Do not edit.
     * 
     * @param l passes in students in line
     * @param m passes in musical chairs
     * @param a passes in availability
     * @param s passes in students sitting
     */
    public Classroom(SNode l, SNode m, boolean[][] a, Student[][] s) {
        studentsInLine = l;
        musicalChairs = m;
        seatingAvailability = a;
        studentsSitting = s;
    }

    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
     * This method simulates students coming into the classroom and standing in
     * line.
     * 
     * Reads students from input file and inserts these students in alphabetical
     * order to studentsInLine singly linked list.
     * 
     * Input file has:
     * 1) one line containing an integer representing the number of students in the
     * file, say x
     * 2) x lines containing one student per line. Each line has the following
     * student
     * information separated by spaces: FirstName LastName Height
     * 
     * @param filename the student information input file
     */

    public void makeClassroom(String filename) {
        Student temp;

        StdIn.setFile(filename);
        int len = StdIn.readInt(); // reads first variable and stores it to len (number of kids in class)

        // makes student array in alphabetical order
        Student[] std = new Student[len];
        for (int i = 0; i < len; i++) {
            std[i] = new Student(StdIn.readString(), StdIn.readString(), StdIn.readInt());
        }
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (std[i].compareNameTo(std[j]) > 0) {
                    temp = std[i];
                    std[i] = std[j];
                    std[j] = temp;
                }
            }
        }

        // makes linked list
        for (int i = len - 1; i >= 0; i--) {
            SNode newNode = new SNode(std[i], studentsInLine);
            studentsInLine = newNode;
        }

    }

    /**
     * 
     * This method creates and initializes the seatingAvailability (2D array) of
     * available seats inside the classroom. Imagine that unavailable seats are
     * broken and cannot be used.
     * 
     * Reads seating chart input file with the format:
     * An integer representing the number of rows in the classroom, say r
     * An integer representing the number of columns in the classroom, say c
     * Number of r lines, each containing c true or false values (true denotes an
     * available seat)
     * 
     * This method also creates the studentsSitting array with the same number of
     * rows and columns as the seatingAvailability array
     * 
     * This method does not seat students on the seats.
     * 
     * @param seatingChart the seating chart input file
     */
    public void setupSeats(String seatingChart) {
        StdIn.setFile(seatingChart);
        int r = StdIn.readInt();
        int c = StdIn.readInt();

        seatingAvailability = new boolean[r][c];
        studentsSitting = new Student[r][c];

        // populates the seatingAvailability array with true and false values
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                seatingAvailability[i][j] = StdIn.readBoolean();
            }
        }

    }

    /**
     * 
     * This method simulates students taking their seats in the classroom.
     * 
     * 1. seats any remaining students from the musicalChairs starting from the
     * front of the list
     * 2. starting from the front of the studentsInLine singly linked list
     * 3. removes one student at a time from the list and inserts them into
     * studentsSitting according to
     * seatingAvailability
     * 
     * studentsInLine will then be empty
     */
    public void seatStudents() {

        // still need to seat remaining students from musical chairs starting from front
        // of the list

        for (int i = 0; i < seatingAvailability.length; i++) {
            for (int j = 0; j < seatingAvailability[0].length; j++) {
                if (seatingAvailability[i][j] == true) {
                    if ((musicalChairs != null)) {
                        studentsSitting[i][j] = musicalChairs.getStudent();
                        musicalChairs = null;
                    } else if ((musicalChairs == null)) {
                        if (studentsInLine == null) {
                            break;
                        } else if (studentsInLine.getNext() != null) {
                            studentsSitting[i][j] = studentsInLine.getStudent();
                            studentsInLine = studentsInLine.getNext();
                        } else {
                            studentsSitting[i][j] = studentsInLine.getStudent();
                            studentsInLine = studentsInLine.getNext();
                            break;
                        }
                    }
                }
            }
        }

    }

    /**
     * Traverses studentsSitting row-wise (starting at row 0) removing a seated
     * student and adding that student to the end of the musicalChairs list.
     * 
     * row-wise: starts at index [0][0] traverses the entire first row and then
     * moves
     * into second row.
     */
    public void insertMusicalChairs() {

        SNode head = new SNode();

        for (int i = 0; i < studentsSitting.length; i++) {
            for (int j = 0; j < studentsSitting[0].length; j++) {
                if ((studentsSitting[i][j] != null) && (musicalChairs == null)) {
                    SNode temp = new SNode(studentsSitting[i][j], null);
                    temp.setNext(temp);
                    musicalChairs = temp;
                    head = temp;
                    studentsSitting[i][j] = null;
                } else if (studentsSitting[i][j] != null) {
                    SNode temp = new SNode(studentsSitting[i][j], null);
                    musicalChairs.setNext(temp);
                    temp.setNext(head);
                    musicalChairs = temp;
                    studentsSitting[i][j] = null;
                }
            }
        }

    }

    /**
     * 
     * This method repeatedly removes students from the musicalChairs until there is
     * only one
     * student (the winner).
     * 
     * Choose a student to be elimnated from the musicalChairs using
     * StdRandom.uniform(int b),
     * where b is the number of students in the musicalChairs. 0 is the first
     * student in the
     * list, b-1 is the last.
     * 
     * Removes eliminated student from the list and inserts students back in
     * studentsInLine
     * in ascending height order (shortest to tallest).
     * 
     * The last line of this method calls the seatStudents() method so that students
     * can be seated.
     */

    // CORRECT
    private int getCountSLL(SNode list) {
        SNode temp = list;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.getNext();
        }
        return count;
    }

    // make an array of Student[] organized by height then put into a linked list
    private void sortListByHeight() {

        int count = getCountSLL(studentsInLine);
        SNode temp = studentsInLine;
        Student[] arr = new Student[count];
        for (int i = 0; i < count; i++) {
            arr[i] = temp.getStudent();
            temp = temp.getNext();
        }

        sortArray(arr);

        studentsInLine = null;

        for (int i = count - 1; i >= 0; i--) { // either this or normal make sure to check
            SNode newNode = new SNode(arr[i], studentsInLine);
            studentsInLine = newNode;
        }

    }

    private Student[] sortArray(Student[] arr) {

        int size = arr.length;
        for (int i = 1; i < size; i++) {
            Student temp = arr[i];
            int num = arr[i].getHeight();
            int j = i - 1;
            while (j >= 0 && num < arr[j].getHeight()) {
                arr[j + 1] = arr[j];
                --j;
            }
            arr[j + 1] = temp;
        }

        return arr;
    }

    // CORRECT
    private int getCountCLL(SNode list) {
        SNode head = list.getNext();
        SNode temp = head;
        int count = 0;
        if (head != null) {
            do {
                temp = temp.getNext();
                count++;
            } while (temp != head);
        }
        return count;
    }

    public void playMusicalChairs() {

        // PLAY MUSICAL CHAIRS AND REMOVE FROM CLL AND STORE INTO studentsInLine

        while (getCountCLL(musicalChairs) != 1) {

            int count = getCountCLL(musicalChairs);
            int rnd = StdRandom.uniform(count);
            Student temp = new Student();

            if (rnd == 0) {
                temp = musicalChairs.getNext().getStudent();
                musicalChairs.setNext(musicalChairs.getNext().getNext());
            } else if (rnd == count - 1) {
                for (int i = 0; i < rnd; i++) {
                    musicalChairs = musicalChairs.getNext();
                }
                temp = musicalChairs.getNext().getStudent();
                musicalChairs.setNext(musicalChairs.getNext().getNext());
            } else {
                for (int i = 0; i < rnd; i++) {
                    musicalChairs = musicalChairs.getNext();
                }
                temp = musicalChairs.getNext().getStudent();
                musicalChairs.setNext(musicalChairs.getNext().getNext());
                for (int i = 0; i < (count - rnd - 1); i++)
                    musicalChairs = musicalChairs.getNext();
            }

            SNode newNode = new SNode(temp, studentsInLine);
            studentsInLine = newNode;
            StdOut.println(rnd);

        }

        sortListByHeight();
        seatStudents();

    }

    /**
     * Insert a student to wherever the students are at (ie. whatever activity is
     * not empty)
     * Note: adds to the end of either linked list or the next available empty seat
     * 
     * @param firstName the first name
     * @param lastName  the last name
     * @param height    the height of the student
     */

    private SNode getHead() {
        SNode head = musicalChairs.getNext();
        return head;
    }

    private SNode getTail() {
        SNode temp = studentsInLine;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        SNode tail = temp;
        return tail;
    }

    public void addLateStudent(String firstName, String lastName, int height) {
        Student late = new Student(firstName, lastName, height);

        if (studentsInLine != null) {
            SNode newNode = new SNode(late, null);
            SNode tail = getTail();
            tail.setNext(newNode);
        } else if (musicalChairs != null) {
            SNode newNode = new SNode(late, null);
            SNode head = getHead();
            musicalChairs.setNext(newNode);
            newNode.setNext(head);
            musicalChairs = newNode;
        } else {
            int temp = 0;
            for (int i = 0; i < studentsSitting.length; i++) {
                for (int j = 0; j < studentsSitting[0].length; j++) {
                    if ((studentsSitting[i][j] == null) && (seatingAvailability[i][j] == true)) {
                        studentsSitting[i][j] = late;
                        temp = j;
                        break;
                    }
                }
                if (studentsSitting[i][temp] == late)
                    break;
            }
        }

    }

    /**
     * A student decides to leave early
     * This method deletes an early-leaving student from wherever the students
     * are at (ie. whatever activity is not empty)
     * 
     * Assume the student's name is unique
     * 
     * @param firstName the student's first name
     * @param lastName  the student's last name
     */

    public void deleteLeavingStudent(String firstName, String lastName) {

        // might need to wrap whole thing in if (studentsInLine != null) {}
        if (studentsInLine != null) {
            SNode copy = studentsInLine;
            int count = getCountSLL(studentsInLine);
            Student[] arr = new Student[count];
            for (int i = 0; i < count; i++) {
                arr[i] = copy.getStudent();
                copy = copy.getNext();
            }
            SNode temp = null;
            if (arr[0].getFirstName().equals(firstName) == true && arr[0].getLastName().equals(lastName) == true) {
                for (int i = count - 1; i >= 1; i--) {
                    SNode newNode = new SNode(arr[i], temp);
                    temp = newNode;
                }
            } else if (arr[count - 1].getFirstName().equals(firstName) == true
                    && arr[count - 1].getLastName().equals(lastName)) {
                for (int i = count - 2; i >= 0; i--) {
                    SNode newNode = new SNode(arr[i], temp);
                    temp = newNode;
                }
            } else {
                int counter = 0;
                for (int i = 0; i < count; i++) {
                    if (arr[i].getFirstName().equals(firstName) == true
                            && arr[i].getLastName().equals(lastName) == true) {
                        break;
                    } else {
                        counter++;
                    }
                }
                arr[counter] = null; // check counter goes to right one
                for (int i = count - 1; i >= 0; i--) {
                    if (arr[i] != null) {
                        SNode newNode = new SNode(arr[i], temp);
                        temp = newNode;
                    }
                }
            }
            studentsInLine = temp;
        } else if (musicalChairs != null) {
            SNode copy = musicalChairs;
            int count = getCountCLL(musicalChairs);
            Student[] arr = new Student[count];
            copy = copy.getNext();
            for (int i = 0; i < count; i++) {
                arr[i] = copy.getStudent();
                StdOut.println(arr[i].getFirstName());
                copy = copy.getNext();
            }

            if (arr[0].getFirstName().equals(firstName) == true && arr[0].getLastName().equals(lastName)) {
                musicalChairs.setNext(musicalChairs.getNext().getNext());
            } else if (arr[count - 1].getFirstName().equals(firstName) == true
                    && arr[count - 1].getLastName().equals(lastName)) {
                for (int i = 0; i < count - 1; i++) {
                    musicalChairs = musicalChairs.getNext();
                }
                musicalChairs.setNext(musicalChairs.getNext().getNext());
            } else {
                SNode head = new SNode();
                int counter = -1;
                SNode t1 = musicalChairs;
                SNode ret = null;
                while (t1.getStudent().getFirstName().equals(firstName) != true
                        && t1.getStudent().getLastName().equals(lastName) != true) {
                    t1 = t1.getNext();
                    counter++;
                }
                for (int i = 0; i < count; i++) {
                    if (i != counter && ret == null) {
                        SNode temp = new SNode(arr[i], null);
                        temp.setNext(temp);
                        ret = temp;
                        head = temp;
                    } else if (i != counter) {
                        SNode temp = new SNode(arr[i], null);
                        ret.setNext(temp);
                        temp.setNext(head);
                        ret = temp;
                    }
                }

                musicalChairs = ret;
            }

        } else {
            for (int i = 0; i < studentsSitting.length; i++) {
                for (int j = 0; j < studentsSitting[0].length; j++) {
                    if (studentsSitting[i][j] != null && studentsSitting[i][j].getFirstName().equals(firstName) == true
                            && studentsSitting[i][j].getLastName().equals(lastName) == true) {
                        studentsSitting[i][j] = null;
                    }
                }
            }
        }

    }

    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine() {

        // Print studentsInLine
        StdOut.println("Students in Line:");
        if (studentsInLine == null) {
            StdOut.println("EMPTY");
        }

        for (SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext()) {
            StdOut.print(ptr.getStudent().print());
            if (ptr.getNext() != null) {
                StdOut.print(" -> ");
            }
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents() {

        StdOut.println("Sitting Students:");

        if (studentsSitting != null) {

            for (int i = 0; i < studentsSitting.length; i++) {
                for (int j = 0; j < studentsSitting[i].length; j++) {

                    String stringToPrint = "";
                    if (studentsSitting[i][j] == null) {

                        if (seatingAvailability[i][j] == false) {
                            stringToPrint = "X";
                        } else {
                            stringToPrint = "EMPTY";
                        }

                    } else {
                        stringToPrint = studentsSitting[i][j].print();
                    }

                    StdOut.print(stringToPrint);

                    for (int o = 0; o < (10 - stringToPrint.length()); o++) {
                        StdOut.print(" ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("EMPTY");
        }
        StdOut.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs() {
        StdOut.println("Students in Musical Chairs:");

        if (musicalChairs == null) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        for (ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext()) {
            StdOut.print(ptr.getStudent().print() + " -> ");
        }
        if (ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() {
        return studentsInLine;
    }

    public void setStudentsInLine(SNode l) {
        studentsInLine = l;
    }

    public SNode getMusicalChairs() {
        return musicalChairs;
    }

    public void setMusicalChairs(SNode m) {
        musicalChairs = m;
    }

    public boolean[][] getSeatingAvailability() {
        return seatingAvailability;
    }

    public void setSeatingAvailability(boolean[][] a) {
        seatingAvailability = a;
    }

    public Student[][] getStudentsSitting() {
        return studentsSitting;
    }

    public void setStudentsSitting(Student[][] s) {
        studentsSitting = s;
    }

}
