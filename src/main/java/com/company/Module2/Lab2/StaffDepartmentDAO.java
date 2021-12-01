package com.company.Module2.Lab2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDepartmentDAO {
    private final Connection connection;

    public StaffDepartmentDAO() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        final String user = "postgres";
        final String password = "password";
        final String url = "jdbc:postgresql://localhost:5432/StaffDepartment";

        connection = DriverManager.getConnection(url, user, password);
    }

    public void stop() throws SQLException {
        connection.close();
    }

    public void showDepartmentUnits() {
        String sql = "SELECT * FROM DEPARTMENTUNITS";
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            System.out.println("Department units:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int numberOfEmployees = resultSet.getInt("numberofemployees");

                System.out.println(new DepartmentUnit(id, name, numberOfEmployees));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error while getting all department units!");
            System.out.println(" >>     " + e.getMessage());
        }
    }

    public boolean addDepartmentUnit(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO DEPARTMENTUNITS " +
                    "(name) " +
                    "VALUES(?)");
            statement.setString(1, name);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error! Department unit " + name + " wasn't added!");
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public DepartmentUnit findDepartmentUnit(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM DEPARTMENTUNITS " +
                    "WHERE id=?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return new DepartmentUnit(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("numberofemployees"));
            }

            return null;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return null;
        }
    }

    public DepartmentUnit findDepartmentUnit(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM DEPARTMENTUNITS " +
                    "WHERE name=?");
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return new DepartmentUnit(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("numberofemployees"));
            }

            return null;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return null;
        }
    }

    public List<DepartmentUnit> findDepartmentUnits(int numberOfEmployees) {
        List<DepartmentUnit> resultList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM DEPARTMENTUNITS " +
                    "WHERE numberofemployees=?");
            statement.setInt(1, numberOfEmployees);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                resultList.add(new DepartmentUnit(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("numberofemployees")));
            }

            return resultList;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return resultList;
        }
    }

    public void deleteAllUnits() {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM DEPARTMENTUNITS");
            statement.executeUpdate();
            System.out.println("Department units table was cleared");
        } catch (SQLException e) {
            System.out.println("Department units table wasn't cleared!");
            System.out.println(" >>     " + e.getMessage());
        }
    }

    public void deleteAllEmployees() {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM EMPLOYEES");
            statement.executeUpdate();
            System.out.println("Employees table was cleared");
        } catch (SQLException e) {
            System.out.println("Employees table wasn't cleared!");
            System.out.println(" >>     " + e.getMessage());
        }
    }

    public boolean deleteDepartmentUnit(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM DEPARTMENTUNITS " +
                    "WHERE id = ?");
            statement.setInt(1, id);
            int exec = statement.executeUpdate();
            if (exec > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting Department unit with " +
                    id);
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public boolean deleteDepartmentUnit(String name) {
        try {
            List<Employee> toDelete = findEmployeesFromUnit(name);

            for (int i = 0; i < toDelete.size(); ++i) {
                deleteEmployee(toDelete.get(i).getId());
            }

            PreparedStatement statement = connection.prepareStatement("DELETE FROM DEPARTMENTUNITS " +
                    "WHERE name = ?");
            statement.setString(1, name);
            int exec = statement.executeUpdate();
            if (exec > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting Department unit with " +
                    name);
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public boolean setDepartmentUnit(DepartmentUnit departmentUnit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE DEPARTMENTUNITS " +
                            "SET name=?, numberofemployees=? " +
                            "WHERE id=?"
            );
            statement.setString(1, departmentUnit.getName());
            statement.setInt(2, departmentUnit.getNumberOfEmployees());
            statement.setInt(3, departmentUnit.getId());
            int exec = statement.executeUpdate();

            if (exec > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public void showEmployees() {
        String sql = "SELECT * FROM EMPLOYEES";
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            System.out.println("Employees:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                boolean isDepartmentHead = resultSet.getBoolean("isdepartmenthead");
                int lengthOfEmployment = resultSet.getInt("lengthofemployment");

                System.out.println(new Employee(id, name, isDepartmentHead, lengthOfEmployment));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
        }
    }

    public boolean addEmployee(String name, boolean isDepartmentHead, int lengthOfEmployment, String unitName) {
        try {
            DepartmentUnit unitToInsert = findDepartmentUnit(unitName);

            if (unitToInsert != null) {
                int unitId = unitToInsert.getId();

                PreparedStatement statement = connection.prepareStatement("INSERT INTO EMPLOYEES " +
                        "(name, isdepartmenthead, lengthofemployment, unitid) " +
                        "VALUES(?, ?, ?, ?)");
                statement.setString(1, name);
                statement.setBoolean(2, isDepartmentHead);
                statement.setInt(3, lengthOfEmployment);
                statement.setInt(4, unitId);
                statement.executeUpdate();

                unitToInsert.increaseNumberOfEmployees();
                setDepartmentUnit(unitToInsert);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public boolean deleteEmployee(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM EMPLOYEES " +
                    "WHERE id = ?");
            statement.setInt(1, id);
            int exec = statement.executeUpdate();
            if (exec > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public boolean deleteEmployee(String name, String unitName) {
        try {
            Employee toDelete = findEmployee(name, unitName);
            if (toDelete != null) {
                DepartmentUnit toSearchIn = findDepartmentUnit(toDelete.getUnitId());

                if (toSearchIn != null) {
                    toSearchIn.decreaseNumberOfEmployees();

                    PreparedStatement statement = connection.prepareStatement("DELETE FROM EMPLOYEES " +
                            "WHERE name = ?");
                    statement.setString(1, name);
                    int exec = statement.executeUpdate();
                    if (exec > 0) {
                        setDepartmentUnit(toSearchIn);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public boolean setEmployee(String name, String unitName, String newName) {
        try {
            Employee toChange = findEmployee(name, unitName);

            if (toChange != null) {
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE EMPLOYEES " +
                                "SET name=? " +
                                "WHERE name=?"
                );
                statement.setString(1, newName);
                statement.setString(2, name);
                int exec = statement.executeUpdate();

                if (exec > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public boolean setEmployee(String name, String unitName, int lengthOfEmployment) {
        try {
            Employee toChange = findEmployee(name, unitName);

            if (toChange != null) {
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE EMPLOYEES " +
                                "SET lengthofemployment=? " +
                                "WHERE name=?"
                );
                statement.setInt(1, lengthOfEmployment);
                statement.setString(2, name);
                int exec = statement.executeUpdate();

                if (exec > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public List<Employee> findDepartmentHeads(boolean isDepartmentHead) {
        List<Employee> resultList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM EMPLOYEES " +
                    "WHERE isdepartmenthead=?");
            statement.setBoolean(1, isDepartmentHead);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                resultList.add(new Employee(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getBoolean("isdepartmenthead"),
                        resultSet.getInt("lengthofemployment"),
                        resultSet.getInt("unitid")));
            }

            return resultList;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return resultList;
        }
    }

    public List<Employee> findEmployees(String name) {
        List<Employee> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM EMPLOYEES " +
                    "WHERE name =?");
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.add(new Employee(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getBoolean("isdepartmenthead"),
                        resultSet.getInt("lengthofemployment"),
                        resultSet.getInt("unitid")));
            }

            return result;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return result;
        }
    }

    public Employee findEmployee(String name, String unitName) {
        try {
            DepartmentUnit unit = findDepartmentUnit(unitName);
            if (unit == null) {
                return null;
            }

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM EMPLOYEES " +
                    "WHERE name =? AND unitid=?");
            statement.setString(1, name);
            statement.setInt(2, unit.getId());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return new Employee(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getBoolean("isdepartmenthead"),
                        resultSet.getInt("lengthofemployment"),
                        resultSet.getInt("unitid"));
            }

            return null;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return null;
        }
    }


    public List<Employee> findEmployees(int lengthOfEmployment) {
        List<Employee> resultList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM EMPLOYEES " +
                    "WHERE lengthofemployment=?");
            statement.setInt(1, lengthOfEmployment);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                resultList.add(new Employee(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getBoolean("isdepartmenthead"),
                        resultSet.getInt("lengthofemployment"),
                        resultSet.getInt("unitid")));
            }

            return resultList;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return resultList;
        }
    }

    public List<Employee> findEmployeesFromUnit(String unitName) {
        List<Employee> resultList = new ArrayList<>();

        try {
            DepartmentUnit unitToFind = findDepartmentUnit(unitName);

            if (unitToFind != null) {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM EMPLOYEES " +
                        "WHERE unitid=?");
                statement.setInt(1, unitToFind.getId());

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    resultList.add(new Employee(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getBoolean("isdepartmenthead"),
                            resultSet.getInt("lengthofemployment"),
                            resultSet.getInt("unitid")));
                }

            }
            return resultList;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return resultList;
        }
    }
}
