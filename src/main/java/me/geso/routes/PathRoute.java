package me.geso.routes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Do not use directly.
 *
 * @param <T>
 */
class PathRoute<T> {
	private final String path;
	private final Pattern pattern;

	private final T destination;
	private final List<String> namedGroups = new ArrayList<>();

	private static final String starKey = "SSSstarSSS";

	private static final String braceNormalPatternRe = "\\{(?<braceName>[a-zA-Z_][a-zA-Z0-9_-]*)\\}";
	private static final String starPatternRe = "\\*";
	// For named regexp matcher (e.g. /{id:[0-9]{5}}/{title:[a-zA-Z_]+})
	private static final String namedRegexMatcherPatternRe =
		"\\{(?<regexName>[^:]+?):(?<regex>.+?)\\}(?<delimiter>/|$)";
	// RegExp meta characters... We should escape these characters.
	private static final String escapePatternRe = "[\\-{}\\[\\]+?\\.,\\\\\\^$|#\\s]";
	private static final Pattern matchPattern = Pattern.compile(
			"(" + braceNormalPatternRe + ")" + "|"
			+ "(" + starPatternRe + ")" + "|"
			+ "(" + namedRegexMatcherPatternRe + ")" + "|"
			+ "(" + escapePatternRe + ")"
	);

	PathRoute(String path, T destination) {
		this.path = path;
		this.pattern = Pattern.compile(compileToRegexp(path));
		this.destination = destination;
	}

	String compileToRegexp(String path) {
		Matcher m = matchPattern.matcher(path);
		StringBuffer sb = new StringBuffer(path.length());
		while (m.find()) {
			if (m.group(1) != null) {
				// {foo}
				namedGroups.add(m.group("braceName"));
				String replace = "(?<" + m.group("braceName")
						+ ">[a-zA-Z0-9._-]+)";
				m.appendReplacement(sb, replace);
			} else if (m.group(3) != null) {
				namedGroups.add(starKey);
				String replace = "(?<" + starKey + ">.+)";
				m.appendReplacement(sb, replace);
			} else if (m.group(4) != null) {
				namedGroups.add(m.group("regexName"));
				String delimiter = m.group("delimiter").equals("/") ? "/" : "";
				m.appendReplacement(sb, "(?<" + m.group("regexName") + ">" + m.group("regex") + ")" + delimiter);
			} else if (m.group(8) != null) {
				// foo.bar ... needs escape meta character
				String replace = "\\\\" + m.group(8);
				m.appendReplacement(sb, replace);
			} else {
				throw new RuntimeException();
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}

	boolean match(final String path, final Map<String, String> captured) {
		Matcher m = pattern.matcher(path);
		if (m.matches()) {
			for (String name : namedGroups) {
				String key = name.equals(starKey) ? "*" : name;
				captured.put(key, m.group(name));
			}
			return true;
		}
		return false;
	}

	T getDestination() {
		return destination;
	}

	String getPath() {
		return path;
	}

}
