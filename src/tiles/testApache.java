//package tiles;
//
//import java.util.Map;
//
//public class testApache {
//
//    private Map<String, Integer> headerMap;
//    private CSVRecord record, recordWithHeader;
//    private String[] values;
//
//    public enum EnumHeader {
//        FIRST("first"), SECOND("second"), THIRD("third");
//
//        private final String number;
//
//        EnumHeader(final String number) {
//            this.number = number;
//        }
//
//        @Override
//        public String toString() {
//            return number;
//        }
//    }
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        values = new String[] { "A", "B", "C" };
//        final String rowData = StringUtils.join(values, ',');
//        try (final CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
//            record = parser.iterator().next();
//        }
//        try (final CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).build().parse(new StringReader(rowData))) {
//            recordWithHeader = parser.iterator().next();
//            headerMap = parser.getHeaderMap();
//        }
//    }
//}
