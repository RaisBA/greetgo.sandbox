package kz.greetgo.sandbox.controller.model;


public enum SortType {
  NON("id"), AGE("age"), TOTAL_SCORE("totalScore"), MAX_SCORE("maxScore"), MIN_SCORE("minScore");

  String columnName;

  SortType(String columnName){
    this.columnName = columnName;
  }

  public String getColumnName(){
    return columnName;
  }

  public static String getColumnNameTo(Integer numOfType){
    if (numOfType == null || numOfType > SortType.values().length){
      return NON.columnName;
    }

    return SortType.values()[numOfType].columnName;

  }
}
