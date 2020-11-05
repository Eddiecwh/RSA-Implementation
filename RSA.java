import java.util.*;

public class RSA {
    // Initializing global variables

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        // String input = "WKHUH DUHWZ RZDBV RIFRQ VWUXF WLQJD VRIWZ DUHGH VLJQR QHZDB LVWRP DNHLW VRVLP SOHWK DWWKH UHDUH REYLR XVOBQ RGHIL FLHQF LHVDQ GWKHR WKHUZ DBLVW RPDNH LWVRF RPSOL FDWHG WKDWW KHUHD UHQRR EYLRX VGHIL FLHQF LHVWK HILUV WPHWK RGLVI DUPRU HGLII LFXOW";
        String input = "CT OSB UHGI TP IPEWF H CEWIL NSTTLE FJNVX XTYLS FWKKHI BJLSI SQ VOI BKSM XMKUL SK NVPONPN GSW OL. IEAG NPSI HYJISFZ CYY NPUXQG TPRJA VXMSI AP EHVPPR TH WPPNEL. UVZUA MMYVSF KNTS ZSZ UAJPQ DLMMJXL JR RA PORTELOGJ CSULTWNI XMKUHW XGLN ELCPOWY OL. ULJTL BVJ TLBWTPZ XLD K ZISZNK OSY DL RYJUAJSSGK. TLFNS UVD VV FQGCYL FJHVSI YJL NEXV PO WTOL PYYYHSH GQBOH AGZTIQ EYFAX YPMP SQA CI XEYVXNPPAII UV TLFTWMC FU WBWXGUHIWU. AIIWG HSI YJVTI BJV. XMQN SFX DQB LRTY TZ QTXLNISVZ. GIFT AII UQSJGJ OHZ XFOWFV BKAI CTWY DSWTLTTTPKFRHG IVX QCAFV TP DIIS JBF ESF JSC MCCF HNGK ESBP DJPQ NLUCTW ROSB CSM.";
        System.out.println("Based on your string, your IC is: " + computeIC(input));
        System.out.println(findTriGram(input));
        System.out.println("Enter the (best fit) greatest common factor: ");
        int howLong = sc.nextInt();
        String key = findKey(input, howLong);
        System.out.println("Your key is: " + key);
        dec(input, key);
        //System.out.println("Your decoded message is: " + dec(input, key));
    }
    
    /* Method to compute Index of Coincidence
     * Takes cypherText as argument(s)
     */
    public static double computeIC(String userInput) {
        int i;
        // Convert string to uppercase
        userInput = userInput.toUpperCase();
        double sum = 0.0;

        // Length of the array, WITHOUT counting the blankspace
        int n = 0;

        //initialize array of values to count frequency of each letter
    	int[] count = new int[26];
    	for (i = 0; i < 26; i++){
    		count[i] = 0;
    	}

        int letter;

        for (i = 0; i < userInput.length(); i++) {
            letter = userInput.charAt(i) - 65;

            // If charAt(i) is between A and Z then increase count of that letter
            if (letter >= 0 && letter < 26) {
                count[letter]++;
                n++;
            }
        }

        /* By defeinition, Index of Coincidence is calculated by the summation of 
        each of the letter count(s) */
        for (i = 0; i < 26; i++) {
            letter = count[i];
            sum = sum + (letter * (letter-1));
        }

        double total = sum/(n*(n-1));

        return total;
    }

    public static ArrayList<Integer> findTriGram(String cipherText) {
        // Remove all white spaces and periods from string
        cipherText = cipherText.replaceAll("\\s", "");
        cipherText = cipherText.replaceAll("\\.", "");

        int length = cipherText.length();

        ArrayList<String> seq = new ArrayList<String>(250);
        // ArrayList<String> unique = new ArrayList<String>();
        ArrayList<String> repeated = new ArrayList<String>();

        ArrayList<Integer> distance = new ArrayList<Integer>();

        // Create an array of trigrams from CipherText
        for (int i = 0; i < length-3; i++) {
        // Make letters i, i+1 and i+2 into a trigram and store in seq
          seq.add(cipherText.substring(i, i+4));
        }

        /* For example array { ABC, CEF, ABC, DHP}
           starting from seq(0), if ABC is found again add ABC to repeated */
        int sequenceLength = seq.size();
        for (int i = 0; i < sequenceLength; i++) {
        // j = i+1 so that we don't repeat already found trigrams
            for (int j = i+1; j < sequenceLength; j++) {
                if (seq.get(i).equals(seq.get(j))) {
                    repeated.add(seq.get(i));
                }
            }
        }

        int first = 0;
        int second = 0;
        int repeatedLength = repeated.size();

        for (int i=0; i < repeatedLength; i++) {
            first = cipherText.indexOf(repeated.get(i));
            second = cipherText.lastIndexOf(repeated.get(i));
            int dist = second - first;
            distance.add(dist);
        }

        return distance;
    }

    public static String findKey(String cipherText, int keyLength) {
        cipherText = cipherText.replaceAll("\\s", "");
        cipherText = cipherText.replaceAll("\\.", "");

        int length = cipherText.length();

        // String[] patterns = new String[length];
        ArrayList<String> patterns = new ArrayList<String>();
        String str = "";
        char c1 = ' ';

        for (int i = 0; i < keyLength; i++) {
            for (int j = i; j < length; j+=keyLength) {
                c1 = cipherText.charAt(j);
                str += c1;
            }
            patterns.add(str);
            str = "";
        }

        String unshifted = " ";

        for (int i = 0; i < patterns.size(); i++) {
            unshifted += mcomchar(patterns.get(i));
        }

        unshifted = unshifted.toUpperCase();

        String key = ""; 

        for (int i = 0; i <= keyLength; i++) {
            c1 = (char)(unshifted.charAt(i) - 'E');
            char c2 = (char)('A' + c1);

            key += c2;
        }

        return key;
    }

    public static char mcomchar(String text) {
        String lowerCase = text.toLowerCase();
		int count[] = new int[256];;

        for (int i=0; i<lowerCase.length(); i++) 
            count[lowerCase.charAt(i)]++; 

        int max = 0;
        char result = ' ';

        for (int i = 0; i < lowerCase.length(); i++) { 
            if (max < count[lowerCase.charAt(i)]) { 
                max = count[lowerCase.charAt(i)]; 
                result = lowerCase.charAt(i); 
            } 
        } 
        return result;
    }

    public static void dec(String cipherText, String key) {
        //Converting plaintext to char array
        char msg[] = cipherText.toCharArray();
        int msglen = msg.length;
        int i,j;
  
        // Creating new char arrays
        char keygenerator[] = new char[msglen];
        char encMsg[] = new char[msglen];
        char decdMsg[] = new char[msglen];
  
        /* Generate key, using keyword in cyclic manner equal to the length of  original message i.e plaintext */
        for(i = 0, j = 0; i <msglen; ++i, ++j)
        {
            if(j == key.length() - 1)
            {
                j = 0;
            }
             keygenerator[i] = key.charAt(j);
        }
 
        //Decryption
        for(i = 0; i < msglen; ++i) {
            decdMsg[i] = (char)((((encMsg[i] - keygenerator[i]) + 26) % 26) + 'A');
        }

        System.out.println("Decrypted Message: " + String.valueOf(decdMsg));
    }
}
