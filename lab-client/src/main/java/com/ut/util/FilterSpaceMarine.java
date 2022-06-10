package com.ut.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ut.common.data.AstartesCategory;
import com.ut.common.data.SpaceMarine;

public final class FilterSpaceMarine {
    private FilterSpaceMarine() {
        throw new UnsupportedOperationException();
    }

    public static List<SpaceMarine> filterList(String field, String typeFilter, String value, List<SpaceMarine> listSpaceMar) {
        List<SpaceMarine> returnlist;
        if (Objects.isNull(listSpaceMar)) {
            return new ArrayList<SpaceMarine>();
        }
        switch (field) {
            case "id" : Long valueID = Long.parseLong(value);
                        returnlist = filterById(listSpaceMar, typeFilter, valueID);
                        break;
            case "name" : returnlist = filterByName(listSpaceMar, typeFilter, value);
                        break;
            case "creation Time" :
                        returnlist = filterByCreationTime(listSpaceMar, typeFilter, ConverterToBundle.parseDateTime(value));
                        break;
            case "x" :
                        Double valueX = Double.parseDouble(value);
                        returnlist = filterByCoordinateX(listSpaceMar, typeFilter, valueX);
                        break;
            case "y" :
                        Long valueY = Long.parseLong(value);
                        returnlist = filterByCoordinateY(listSpaceMar, typeFilter, valueY);
                        break;
            case "health" :
                        Integer valueHealth = Integer.parseInt(value);
                        returnlist = filterByHealth(listSpaceMar, typeFilter, valueHealth);
                        break;
            case "heartCount" :
                        Integer valueHeartCount = Integer.parseInt(value);
                        returnlist = filterByHeartCount(listSpaceMar, typeFilter, valueHeartCount);
                        break;
            default : returnlist = partTwoOfFilter(field, value, typeFilter, listSpaceMar);
        }
        return returnlist;
    }

    private static List<SpaceMarine> partTwoOfFilter(String field, String value, String typeFilter, List<SpaceMarine> listSpaceMar) {
        List<SpaceMarine> returnlist;
        switch (field) {
            case "loyal" :
                        Boolean valueLoyal;
                        if ("null".equals(value)) {
                            valueLoyal = null;
                        } else {
                            valueLoyal = Boolean.parseBoolean(value);
                        }
                        returnlist = filterByLoyal(listSpaceMar, typeFilter, valueLoyal);
                        break;
            case "category" :
                        AstartesCategory valueCategory = AstartesCategory.valueOf(value.toUpperCase());
                        returnlist = filterByCategory(listSpaceMar, typeFilter, valueCategory);
                        break;
            case "chapter" :
                        returnlist = filterByChapter(listSpaceMar, typeFilter, value);
                        break;
            case "parentLegion" :
                        returnlist = filterByParentLegion(listSpaceMar, typeFilter, value);
                        break;
            case "marinesCount" :
                        Long valueMarinesCount = Long.parseLong(value);
                        returnlist = filterByMarinesCount(listSpaceMar, typeFilter, valueMarinesCount);
                        break;
            case "world" :
                        returnlist = filterByWorld(listSpaceMar, typeFilter, value);
                        break;
            default : returnlist = filterByOwnerName(listSpaceMar, typeFilter, value);
        }
        return returnlist;
    }

    public static List<SpaceMarine> filterById(List<SpaceMarine> listSpaceMar, String typeFilter, Long value) {
        if ("lower".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getID().compareTo(value) < 0).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getID().compareTo(value) > 0).collect(Collectors.toList());
        }
        return listSpaceMar.stream().filter(x -> x.getID().compareTo(value) == 0).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByName(List<SpaceMarine> listSpaceMar, String typeFilter, String value) {
        if ("lower".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getName().compareTo(value) < 0).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getName().compareTo(value) > 0).collect(Collectors.toList());

        }
        return listSpaceMar.stream().filter(x -> x.getName().compareTo(value) == 0).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByCreationTime(List<SpaceMarine> listSpaceMar, String typeFilter, LocalDateTime date) {
        if ("lower".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getCreationDateTime().compareTo(date) < 0).collect(Collectors.toList());

        }
        if ("greater".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getCreationDateTime().compareTo(date) > 0).collect(Collectors.toList());

        }
        return listSpaceMar.stream().filter(x -> x.getCreationDateTime().compareTo(date) == 0).collect(Collectors.toList());

    }

    public static List<SpaceMarine> filterByCoordinateX(List<SpaceMarine> listSpaceMar, String typeFilter, double value) {
        if ("lower".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getCoordinates().getX() < value).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getCoordinates().getX() > value).collect(Collectors.toList());
        }
        return listSpaceMar.stream().filter(x -> x.getCoordinates().getX() == value).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByCoordinateY(List<SpaceMarine> listSpaceMar, String typeFilter, Long value) {
        if ("lower".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getCoordinates().getY().compareTo(value) < 0).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getCoordinates().getY().compareTo(value) > 0).collect(Collectors.toList());
        }
        return listSpaceMar.stream().filter(x -> x.getCoordinates().getY().compareTo(value) == 0).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByHealth(List<SpaceMarine> listSpaceMar, String typeFilter, Integer value) {
        if ("lower".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getHealth().compareTo(value) < 0).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getHealth().compareTo(value) > 0).collect(Collectors.toList());
        }
        return listSpaceMar.stream().filter(x -> x.getHealth().compareTo(value) == 0).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByHeartCount(List<SpaceMarine> listSpaceMar, String typeFilter, Integer value) {
        if ("lower".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getHeartCount().compareTo(value) < 0).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getHeartCount().compareTo(value) > 0).collect(Collectors.toList());
        }
        return listSpaceMar.stream().filter(x -> x.getHeartCount().compareTo(value) == 0).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByLoyal(List<SpaceMarine> listSpaceMar, String typeFilter, Boolean value) {
        if ("lower".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> {
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
            return listSpaceMar.stream().filter(x -> {
                if (x.getLoyal() == null && value == null) {
                    return true;
                } else if (x.getLoyal() == null) {
                    return false;
                } else {
                    return x.getLoyal().compareTo(value) > 0;
                }
            }).collect(Collectors.toList());
        }
        return listSpaceMar.stream().filter(x -> {
            if (x.getLoyal() == null && value == null) {
                return true;
            } else if (x.getLoyal() == null) {
                return false;
            } else {
                return x.getLoyal().compareTo(value) == 0;
            }
        }).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByCategory(List<SpaceMarine> listSpaceMar, String typeFilter, AstartesCategory value) {
        if ("lower".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getCategory().compareTo(value) < 0).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getCategory().compareTo(value) > 0).collect(Collectors.toList());
        }
        return listSpaceMar.stream().filter(x -> x.getCategory().compareTo(value) == 0).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByChapter(List<SpaceMarine> listSpaceMar, String typeFilter, String value) {
        if ("lower".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> {
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
            return listSpaceMar.stream().filter(x -> {
                if (x.getChapter() == null && value == null) {
                    return false;
                } else if (x.getChapter() == null) {
                    return false;
                } else {
                    return x.getChapter().getName().compareTo(value) > 0;
                }
            }).collect(Collectors.toList());
        }
        return listSpaceMar.stream().filter(x -> {
            if (x.getChapter() == null && value == null) {
                return false;
            } else if (x.getChapter() == null) {
                return false;
            } else {
                return x.getChapter().getName().compareTo(value) == 0;
            }
        }).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByParentLegion(List<SpaceMarine> listSpaceMar, String typeFilter, String value) {
        if ("lower".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> {
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
            return listSpaceMar.stream().filter(x -> {
                if (x.getChapter() == null && value == null) {
                    return false;
                } else if (x.getChapter() == null) {
                    return false;
                } else {
                    return x.getChapter().getParentLegion().compareTo(value) > 0;
                }
            }).collect(Collectors.toList());
        }
        return listSpaceMar.stream().filter(x -> {
            if (x.getChapter() == null && value == null) {
                return false;
            } else if (x.getChapter() == null) {
                return false;
            } else {
                return x.getChapter().getParentLegion().compareTo(value) == 0;
            }
        }).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByMarinesCount(List<SpaceMarine> listSpaceMar, String typeFilter, long value) {
        if ("lower".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> {
                if (x.getChapter() == null) {
                    return false;
                } else {
                    return (x.getChapter().getMarinesCount() - value) < 0;
                }
            }).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> {
                if (x.getChapter() == null) {
                    return false;
                } else {
                    return (x.getChapter().getMarinesCount() - value) > 0;
                }
            }).collect(Collectors.toList());
        }
        return listSpaceMar.stream().filter(x -> {
            if (x.getChapter() == null) {
                return false;
            } else {
                return (x.getChapter().getMarinesCount() - value) == 0;
            }
        }).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByWorld(List<SpaceMarine> listSpaceMar, String typeFilter, String value) {
        if ("lower".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> {
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
            return listSpaceMar.stream().filter(x -> {
                if (x.getChapter() == null && value == null) {
                    return false;
                } else if (x.getChapter() == null) {
                    return false;
                } else {
                    return x.getChapter().getWorld().compareTo(value) > 0;
                }
            }).collect(Collectors.toList());
        }
        return listSpaceMar.stream().filter(x -> {
            if (x.getChapter() == null && value == null) {
                return false;
            } else if (x.getChapter() == null) {
                return false;
            } else {
                return x.getChapter().getWorld().compareTo(value) == 0;
            }
        }).collect(Collectors.toList());
    }

    public static List<SpaceMarine> filterByOwnerName(List<SpaceMarine> listSpaceMar, String typeFilter, String value) {
        if ("lower".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getOwnerName().compareTo(value) < 0).collect(Collectors.toList());
        }
        if ("greater".equals(typeFilter)) {
            return listSpaceMar.stream().filter(x -> x.getOwnerName().compareTo(value) > 0).collect(Collectors.toList());
        }
        return listSpaceMar.stream().filter(x -> x.getOwnerName().compareTo(value) == 0).collect(Collectors.toList());
    }
}
