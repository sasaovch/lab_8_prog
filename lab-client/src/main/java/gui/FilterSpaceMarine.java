package gui;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.List;
import java.util.Locale.Category;
import java.util.stream.Collectors;

import com.ut.common.data.AstartesCategory;
import com.ut.common.data.SpaceMarine;

public class FilterSpaceMarine {

    public static List<SpaceMarine> filterList(String field, String typeFilter, String value, List<SpaceMarine> setSpaceMar) {
        List<SpaceMarine> returnlist;
        switch (field) {
            case "id" : Long valueID = Long.parseLong(value);
                        returnlist = filterById(setSpaceMar, typeFilter, valueID);
                        break;
            case "name" : returnlist = filterByName(setSpaceMar, typeFilter, value);
                        break;
            case "coordinate x" : 
                        Double valueX = Double.parseDouble(value);
                        returnlist = filterByCoordinateX(setSpaceMar, typeFilter, valueX);
                        break;
            case "coordinate y" : 
                        Long valueY = Long.parseLong(value);
                        returnlist = filterByCoordinateY(setSpaceMar, typeFilter, valueY);
                        break;
            case "health" :
                        Integer valueHealth = Integer.parseInt(value); 
                        returnlist = filterByHealth(setSpaceMar, typeFilter, valueHealth);
                        break;  
            case "heart count" : 
                        Integer valueHeartCount = Integer.parseInt(value);
                        returnlist = filterByHeartCount(setSpaceMar, typeFilter, valueHeartCount);
                        break;
            case "loyal" : 
                        Boolean valueLoyal;
                        if ("null".equals(value)) {
                            valueLoyal = null;
                        } else {
                            valueLoyal = Boolean.parseBoolean(value);
                        }
                        returnlist = filterByLoyal(setSpaceMar, typeFilter, valueLoyal);
                        break;
            case "category" : 
                        AstartesCategory valueCategory = AstartesCategory.valueOf(value);
                        returnlist = filterByCategory(setSpaceMar, typeFilter, valueCategory);
                        break;
            case "chapter" : 
                        returnlist = filterByChapter(setSpaceMar, typeFilter, value);
                        break;
            case "parent Legion" : 
                        returnlist = filterByParentLegion(setSpaceMar, typeFilter, value);
                        break;
            case "marines Count" : 
                        Long valueMarinesCount = Long.parseLong(value);
                        returnlist = filterByMarinesCount(setSpaceMar, typeFilter, valueMarinesCount);
                        break;
            default : returnlist = filterByWorld(setSpaceMar, typeFilter, value);
        }
        return returnlist;
    }  

    public static List<SpaceMarine> filterById(List<SpaceMarine> setSpaceMar, String typeFilter, Long value) {
        if ("lower".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> x.getID().compareTo(value) < 0).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> x.getID().compareTo(value) > 0).collect(Collectors.toList());
        }
        return setSpaceMar.stream().filter(x -> x.getID().compareTo(value) == 0).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByName(List<SpaceMarine> setSpaceMar, String typeFilter, String value) {
        if ("lower".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> x.getName().compareTo(value) < 0).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> x.getName().compareTo(value) > 0).collect(Collectors.toList());

        }
        return setSpaceMar.stream().filter(x -> x.getName().compareTo(value) == 0).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByCoordinateX(List<SpaceMarine> setSpaceMar, String typeFilter, double value) {
        if ("lower".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> x.getCoordinates().getX() < value).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> x.getCoordinates().getX() > value).collect(Collectors.toList());
        }
        return setSpaceMar.stream().filter(x -> x.getCoordinates().getX() == value).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByCoordinateY(List<SpaceMarine> setSpaceMar, String typeFilter, Long value) {
        if ("lower".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> x.getCoordinates().getY().compareTo(value) < 0).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> x.getCoordinates().getY().compareTo(value) > 0).collect(Collectors.toList());
        }
        return setSpaceMar.stream().filter(x -> x.getCoordinates().getY().compareTo(value) == 0).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByHealth(List<SpaceMarine> setSpaceMar, String typeFilter, Integer value) {
        if ("lower".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> x.getHealth().compareTo(value) < 0).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> x.getHealth().compareTo(value) > 0).collect(Collectors.toList());
        }
        return setSpaceMar.stream().filter(x -> x.getHealth().compareTo(value) == 0).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByHeartCount(List<SpaceMarine> setSpaceMar, String typeFilter, Integer value) {
        if ("lower".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> x.getHeartCount().compareTo(value) < 0).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> x.getHeartCount().compareTo(value) > 0).collect(Collectors.toList());
        }
        return setSpaceMar.stream().filter(x -> x.getHeartCount().compareTo(value) == 0).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByLoyal(List<SpaceMarine> setSpaceMar, String typeFilter, Boolean value) {
        if ("lower".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> {
                if (x.getLoyal() == null && value == null) {
                    return true;
                } else if (x.getLoyal() == null) {
                    return false;
                } else {
                    return x.getLoyal().compareTo(value) < 0;
                }
            }).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> {
                if (x.getLoyal() == null && value == null) {
                    return true;
                } else if (x.getLoyal() == null) {
                    return false;
                } else {
                    return x.getLoyal().compareTo(value) > 0;
                }
            }).collect(Collectors.toList());
        }
        return setSpaceMar.stream().filter(x -> {
            if (x.getLoyal() == null && value == null) {
                return true;
            } else if (x.getLoyal() == null) {
                return false;
            } else {
                return x.getLoyal().compareTo(value) == 0;
            }
        }).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByCategory(List<SpaceMarine> setSpaceMar, String typeFilter, AstartesCategory value) {
        if ("lower".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> x.getCategory().compareTo(value) < 0).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> x.getCategory().compareTo(value) > 0).collect(Collectors.toList());
        }
        return setSpaceMar.stream().filter(x -> x.getCategory().compareTo(value) == 0).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByChapter(List<SpaceMarine> setSpaceMar, String typeFilter, String value) {
        if ("lower".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> {
                if (x.getChapter() == null && value == null) {
                    return true;
                } else if (x.getChapter() == null) {
                    return false;
                } else {
                    return x.getChapter().getName().compareTo(value) < 0;
                }
            }).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> {
                if (x.getChapter() == null && value == null) {
                    return false;
                } else if (x.getChapter() == null) {
                    return false;
                } else {
                    return x.getChapter().getName().compareTo(value) > 0;
                }
            }).collect(Collectors.toList());
        }
        return setSpaceMar.stream().filter(x -> {
            if (x.getChapter() == null && value == null) {
                return false;
            } else if (x.getChapter() == null) {
                return false;
            } else {
                return x.getChapter().getName().compareTo(value) == 0;
            }
        }).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByParentLegion(List<SpaceMarine> setSpaceMar, String typeFilter, String value) {
        if ("lower".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> {
                if (x.getChapter() == null && value == null) {
                    return true;
                } else if (x.getChapter() == null) {
                    return false;
                } else {
                    return x.getChapter().getParentLegion().compareTo(value) < 0;
                }
            }).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> {
                if (x.getChapter() == null && value == null) {
                    return false;
                } else if (x.getChapter() == null) {
                    return false;
                } else {
                    return x.getChapter().getParentLegion().compareTo(value) > 0;
                }
            }).collect(Collectors.toList());
        }
        return setSpaceMar.stream().filter(x -> {
            if (x.getChapter() == null && value == null) {
                return false;
            } else if (x.getChapter() == null) {
                return false;
            } else {
                return x.getChapter().getParentLegion().compareTo(value) == 0;
            }
        }).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByMarinesCount(List<SpaceMarine> setSpaceMar, String typeFilter, long value) {
        if ("lower".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> {
                if (x.getChapter() == null) {
                    return false;
                } else {
                    return (x.getChapter().getMarinesCount() - value) < 0;
                }
            }).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> {
                if (x.getChapter() == null) {
                    return false;
                } else {
                    return (x.getChapter().getMarinesCount() - value) > 0;
                }
            }).collect(Collectors.toList());
        }
        return setSpaceMar.stream().filter(x -> {
            if (x.getChapter() == null) {
                return false;
            } else {
                return (x.getChapter().getMarinesCount() - value) == 0;
            }
        }).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByWorld(List<SpaceMarine> setSpaceMar, String typeFilter, String value) {
        if ("lower".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> {
                if (x.getChapter() == null && value == null) {
                    return true;
                } else if (x.getChapter() == null) {
                    return false;
                } else {
                    return x.getChapter().getWorld().compareTo(value) < 0;
                }
            }).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return setSpaceMar.stream().filter(x -> {
                if (x.getChapter() == null && value == null) {
                    return false;
                } else if (x.getChapter() == null) {
                    return false;
                } else {
                    return x.getChapter().getWorld().compareTo(value) > 0;
                }
            }).collect(Collectors.toList());
        }
        return setSpaceMar.stream().filter(x -> {
            if (x.getChapter() == null && value == null) {
                return false;
            } else if (x.getChapter() == null) {
                return false;
            } else {
                return x.getChapter().getWorld().compareTo(value) == 0;
            }
        }).collect(Collectors.toList());
    }
}
