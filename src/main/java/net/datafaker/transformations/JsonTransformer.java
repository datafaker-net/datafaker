package net.datafaker.transformations;

import net.datafaker.sequence.FakeSequence;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class JsonTransformer<IN> implements Transformer<IN, Object> {
  private static final Map<Character, String> ESCAPING_MAP = createEscapeMap();

  @Override
  public String apply(IN input, Schema<IN, ?> schema) {
    Field<?, ?>[] fields = schema.getFields();
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    for (int i = 0; i < fields.length; i++) {
      value2String((fields[i].getName()), sb);
      sb.append(": ");
      if (fields[i] instanceof CompositeField) {
        sb.append(apply(input, (CompositeField) fields[i], i));
      } else {
        value2String(((SimpleField) fields[i]).transform(input), sb);
      }
      if (i < fields.length - 1) {
        sb.append(", ");
      }
    }
    sb.append("}");
    return sb.toString();
  }

  @Override
  public String generate(FakeSequence<IN> input, Schema<IN, ?> schema) {
    if (input.isInfinite()) {
      throw new IllegalArgumentException("The sequence should be finite of size");
    }

    StringJoiner data = new StringJoiner(LINE_SEPARATOR);
    for (IN in : input) {
      data.add(apply(in, schema));
    }

    return data.length() > 1 ? "{" + LINE_SEPARATOR + data + LINE_SEPARATOR + "}" : data.toString();
  }

  @Override
  public String generate(Schema<IN, ?> schema, int limit) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < limit; i++) {
      sb.append(apply(null, schema, i));
      if (i < limit - 1) {
        sb.append(",").append(LINE_SEPARATOR);
      }
    }
    return limit > 1 ? "{" + LINE_SEPARATOR + sb + LINE_SEPARATOR + "}" : sb.toString();
  }

  private static void value2String(Object value, StringBuilder sb) {
    if (value == null) {
      sb.append("null");
    } else if (value instanceof Integer
        || value instanceof Long
        || value instanceof Short
        || value instanceof BigInteger
        || value instanceof Boolean
        || (value instanceof Double
            && BigDecimal.valueOf((Double) value).remainder(BigDecimal.ONE).doubleValue() == 0)
        || (value instanceof BigDecimal
            && ((BigDecimal) value).remainder(BigDecimal.ONE).doubleValue() == 0)) {
      sb.append(value);
    } else if (value instanceof Collection) {
      sb.append(generate((Collection) value));
    } else if (value.getClass().isArray()) {
      sb.append(generate(Arrays.asList((Object[]) value)));
    } else {
      String val = String.valueOf(value);
      boolean toWrap = !val.startsWith("#{json");
      if (toWrap) {
        sb.append("\"");
      }
      for (char c : String.valueOf(value).toCharArray()) {
        sb.append(ESCAPING_MAP.getOrDefault(c, c + ""));
      }
      if (toWrap) {
        sb.append("\"");
      }
    }
  }

  private static String generate(Collection<Object> collection) {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    int i = 0;
    for (Object value : collection) {
      if (i > 0) {
        sb.append(", ");
      }
      i++;
      value2String(value, sb);
    }
    sb.append("]");
    return sb.toString();
  }

  private static Map<Character, String> createEscapeMap() {
    final Map<Character, String> map = new HashMap<>();
    map.put('\\', "\\\\");
    map.put('\"', "\\\"");
    map.put('\b', "\\b");
    map.put('\f', "\\f");
    map.put('\n', "\\n");
    map.put('\r', "\\r");
    map.put('\t', "\\t");
    map.put('/', "\\/");
    map.put('\u0000', "\\u0000");
    map.put('\u0001', "\\u0001");
    map.put('\u0002', "\\u0002");
    map.put('\u0003', "\\u0003");
    map.put('\u0004', "\\u0004");
    map.put('\u0005', "\\u0005");
    map.put('\u0006', "\\u0006");
    map.put('\u0007', "\\u0007");
    // map.put('\u0008', "\\u0008");
    // covered by map.put('\b', "\\b");
    // map.put('\u0009', "\\u0009");
    // covered by map.put('\t', "\\t");
    // map.put((char) 10, "\\u000A");
    // covered by map.put('\n', "\\n");
    map.put('\u000B', "\\u000B");
    // map.put('\u000C', "\\u000C");
    // covered by map.put('\f', "\\f");
    // map.put((char) 13, "\\u000D");
    // covered by map.put('\r', "\\r");
    map.put('\u000E', "\\u000E");
    map.put('\u000F', "\\u000F");
    map.put('\u0010', "\\u0010");
    map.put('\u0011', "\\u0011");
    map.put('\u0012', "\\u0012");
    map.put('\u0013', "\\u0013");
    map.put('\u0014', "\\u0014");
    map.put('\u0015', "\\u0015");
    map.put('\u0016', "\\u0016");
    map.put('\u0017', "\\u0017");
    map.put('\u0018', "\\u0018");
    map.put('\u0019', "\\u0019");
    map.put('\u001A', "\\u001A");
    map.put('\u001B', "\\u001B");
    map.put('\u001C', "\\u001C");
    map.put('\u001D', "\\u001D");
    map.put('\u001E', "\\u001E");
    map.put('\u001F', "\\u001F");
    return Collections.unmodifiableMap(map);
  }
}
