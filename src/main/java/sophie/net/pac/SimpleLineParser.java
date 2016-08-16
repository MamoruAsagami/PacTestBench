/*
 * Copyright(c) 2016 Mamoru Asagami. All rights reserved.
 * 
*/

package sophie.net.pac;

import java.util.ArrayList;

// Grammar
//
// line = (token)* comment?
// token = special-character | string
// string = (non-whitespace-characters | protected-string)+
// protected-string = " (any-char|escaped-char)* "
// comment = commentIntroducer (any-char)*
//
// commentIntroducer is checked at the beginning of token not in the middle of the token.

public class SimpleLineParser {
	char[] commentIntroducer;
	char[] line;
	String specialChars;
	
	public SimpleLineParser() {
	}
	
	public void setCommentIntroducer(String commentIntroducer) {
		this.commentIntroducer = commentIntroducer.toCharArray();
		if(this.commentIntroducer.length == 0)
			throw new IllegalArgumentException("commentIntroducer.length == 0");
	}
	
	public void setSpecialChars(String specialChars) {
		this.specialChars = specialChars;
	}
	
	int skipWhileSpaces(int index) {
		while(index < line.length && Character.isWhitespace(line[index])) {
			index++;
		}
		return index;
	}
	
	private boolean isSpecialChar(int index) {
		if(specialChars == null) {
			return false;
		} else {
			assert index < line.length;
			return specialChars.indexOf(line[index]) >= 0;
		}
	}
	private boolean isComment(int index) {
		if(commentIntroducer == null) {
			return false;
		}
		int i;
		for(i = 0; i < commentIntroducer.length && index < line.length; i++, index++) {
			if(line[index] != commentIntroducer[i])
				break;
		}
		return i == commentIntroducer.length;
	}
	
	private String[] parse() {
		ArrayList<String> tokenList = new ArrayList<String>();
		int index = 0;
		while(index < line.length) {
			index = skipWhileSpaces(index);
			if(index >= line.length)
				break;
			if(isComment(index))
				break;
			if(isSpecialChar(index)) {
				tokenList.add(new String(line, index, 1));
				index++;
			} else {
				StringBuffer tokenBuffer = new StringBuffer();
				while(index < line.length) {
					if(Character.isWhitespace(line[index]))
						break;
					if(isSpecialChar(index))
						break;
					char c = line[index++];
					if(c == '"') {
						if(index < line.length) {
							c = line[index++];
							while(c != '"') {
								if(c == '\\') {
									if(index < line.length) {
										c = line[index++];
									} else {
										throw new IllegalArgumentException("Dangling \\.");
									}
								}
								tokenBuffer.append(c);
								if(index >= line.length) {
									throw new IllegalArgumentException("Dangling \".");
								}
								c = line[index++];
							}
						} else {
							throw new IllegalArgumentException("Dangling \".");
						}
					} else {
						tokenBuffer.append(c);
					}
				}
				tokenList.add(tokenBuffer.toString());
			}
		}
		return tokenList.toArray(new String[tokenList.size()]);
	}
	
	public String[] parse(String line) {
		this.line = line.toCharArray();
		return parse();
	}

/*
	public static void parserTest(String[] lines) throws IOException {
		SimpleLineParser parser = new SimpleLineParser();
		parser.setCommentIntroducer("#");
		parser.setSpecialChars("(),");

		for(int n = 0; n < lines.length; n++) {
			String line = lines[n];
			System.out.println("line: " + line);
			try {
				String[] tokens = parser.parse(line);
				for(int i = 0; i < tokens.length; i++) {
					System.out.println(i + ":|" + tokens[i] + "|");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
    public static void main(String[] args) throws Exception {
    	try {
    		String[] lines = {
    				" 12 34 5",
    				" -a  b c // comm# #co",
    				"   # comment",
    				"\" b c \" -s\"a \\\"b \\\\c\"\"xy z\"",
    				" 12 3,,4 (5,6,7)",
    				"  12 3,,4 ( 5 , 6 , 7 )"
    		};
    		parserTest(lines);
        } catch(Exception e) {
        	e.printStackTrace(System.err);
        }
    }
*/
}
