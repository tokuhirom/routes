package me.geso.routes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathRoute<T> {
	protected Pattern pattern;

	protected T destination;
	protected List<String> namedGroups = new ArrayList<>();

	static private String starKey = "SSSstarSSS";

	static private String braceNormalPatternRe = "\\{(?<braceName>[a-zA-Z_][a-zA-Z0-9_-]*)\\}";
	static private String starPatternRe = "\\*";
	static private String escapePatternRe = "[\\-{}\\[\\]+?\\.,\\\\\\^$|#\\s]";
	static private Pattern matchPattern = Pattern.compile(String.format(
			"(%s)|(%s)|(%s)", braceNormalPatternRe, starPatternRe,
			escapePatternRe));

	public PathRoute(String path, T destination) {
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
						+ ">[a-zA-Z0-9]+)";
				m.appendReplacement(sb, replace);
			} else if (m.group(3) != null) {
				namedGroups.add(starKey);
				String replace = String.format("(?<%s>.+)", starKey);
				m.appendReplacement(sb, replace);
			} else if (m.group(4) != null) {
				// foo.bar ... needs escape meta character
				String replace = "\\\\" + m.group(4);
				m.appendReplacement(sb, replace);
			} else {
				throw new RuntimeException();
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public RoutingResult<T> match(String path) {
		Matcher m = pattern.matcher(path);
		if (m.matches()) {
			RoutingResult<T> result = new RoutingResult<T>();
			result.methodAllowed = true;
			result.destination = this.destination;
			for (String name : namedGroups) {
				String key = name.equals(starKey) ? "*" : name;
				result.putCaptured(key, m.group(name));
			}
			return result;
		}
		return null;
	}

	public T getDestination() {
		return destination;
	}

}
