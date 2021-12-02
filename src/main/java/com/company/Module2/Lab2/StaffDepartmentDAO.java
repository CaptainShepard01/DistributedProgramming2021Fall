package com.company.Module2.Lab2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDepartmentDAO implements DAOInterface{
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

                System.out.println(new DepartmentUnit(id, name));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error while getting all department units!");
            System.out.println(" >>     " + e.getMessage());
        }
    }

    public List<DepartmentUnit> getDepartmentUnits() {
        String sql = "SELECT * FROM DEPARTMENTUNITS";
        List<DepartmentUnit> result = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            System.out.println("Department units:");
            while (resultSet.next()) {
                result.add(new DepartmentUnit(resultSet.getInt("id"),
                        resultSet.getString("name")));
            }
            resultSet.close();
            return result;
        } catch (SQLException e) {
            System.out.println("Error while getting all department units!");
            System.out.println(" >>     " + e.getMessage());
            return result;
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
                        resultSet.getString("name"));
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
                        resultSet.getString("name"));
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
                    "INNER JOIN EMPLOYEES ON DEPARTMENTUNITS.id = EMPLOYEES.unitid " +
                    "GROUP BY DEPARTMENTUNITS.name " +
                    "HAVING COUNT(*) = ?");
            statement.setInt(1, numberOfEmployees);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                resultList.add(new DepartmentUnit(resultSet.getInt("id"),
                        resultSet.getString("name")));
            }

            return resultList;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return resultList;
        }
    }

    public void deleteAllUnits() {
        try {
            PreparedStatement statement = connection.prepareStatement("TRUNCATE DEPARTMENTUNITS RESTART IDENTITY CASCADE");
            statement.executeUpdate();
            System.out.println("Department units table was cleared");
        } catch (SQLException e) {
            System.out.println("Department units table wasn't cleared!");
            System.out.println(" >>     " + e.getMessage());
        }
    }

    public void deleteAllEmployees() {
        try {
            PreparedStatement statement = connection.prepareStatement("TRUNCATE EMPLOYEES RESTART IDENTITY CASCADE;");
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
            PreparedStatement statement1 = connection.prepareStatement("DELETE FROM EMPLOYEES " +
                    "WHERE unitid = " +
                    "(SELECT id FROM DEPARTMENTUNITS WHERE name=?)");
            statement1.setString(1, name);

            PreparedStatement statement = connection.prepareStatement("DELETE FROM DEPARTMENTUNITS " +
                    "WHERE name = ?");
            statement.setString(1, name);

            statement1.executeUpdate();
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
                            "SET name=?" +
                            "WHERE id=?"
            );
            statement.setString(1, departmentUnit.getName());
            statement.setInt(2, departmentUnit.getId());
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

    @Override
    public String addNewUnit(String[] params) {
        return String.valueOf(addDepartmentUnit(params[1]));
    }

    @Override
    public String deleteUnit(String[] params) {
        return String.valueOf(deleteDepartmentUnit(params[1]));
    }

    @Override
    public String addEmployeeInUnit(String[] params) {
        return String.valueOf(addEmployee(params[1],
                Boolean.parseBoolean(params[2]),
                Integer.parseInt(params[3]),
                params[4]));
    }

    @Override
    public String deleteEmployeeFromUnit(String[] params) {
        return String.valueOf(deleteEmployee(params[1], params[2]));
    }

    @Override
    public String changeEmployeeName(String[] params) {
        return String.valueOf(setEmployee(params[1], params[2], params[3],
                Boolean.parseBoolean(params[4])));
    }

    @Override
    public String changeEmployeeUnit(String[] params) {
        return String.valueOf(setEmployee(params[1], params[2], params[3],
                Boolean.parseBoolean(params[4])));
    }

    @Override
    public String countEmployeesInUnit(String[] params) {
        return null;
    }

    @Override
    public String getEmployeesFromUnit(String[] params) {
        return String.valueOf(countEmployees(params[1]));
    }

    @Override
    public String getUnitsList(String[] params) {
        return String.valueOf(findEmployeesFromUnit(params[1]));
    }

    public boolean addEmployee(String name, boolean isDepartmentHead, int lengthOfEmployment, String unitName) {
        try {
            PreparedStatement statement1 = connection.prepareStatement("SELECT id FROM DEPARTMENTUNITS " +
                    "WHERE name = ?");
            statement1.setString(1, unitName);
            ResultSet set = statement1.executeQuery();

            int unitId = -1;

            if (set.next()) {
                unitId = set.getInt("id");
            }

            PreparedStatement statement = connection.prepareStatement("INSERT INTO EMPLOYEES " +
                    "(name, isdepartmenthead, lengthofemployment, unitid) " +
                    "VALUES(?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setBoolean(2, isDepartmentHead);
            statement.setInt(3, lengthOfEmployment);
            statement.setInt(4, unitId);
            statement.executeUpdate();

            return true;
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
            PreparedStatement statement1 = connection.prepareStatement("SELECT id FROM DEPARTMENTUNITS " +
                    "WHERE name = ?");
            statement1.setString(1, unitName);
            ResultSet set = statement1.executeQuery();

            int unitId = -1;

            if (set.next()) {
                unitId = set.getInt("id");
            }

            PreparedStatement statement = connection.prepareStatement("DELETE FROM EMPLOYEES " +
                    "WHERE name = ? AND unitid = ?");
            statement.setString(1, name);
            statement.setInt(2, unitId);
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

    public boolean setEmployee(String name, String unitName, String newName, boolean isNewUnitName) {
        try {
            PreparedStatement statement1 = connection.prepareStatement("SELECT id FROM DEPARTMENTUNITS " +
                    "WHERE name = ?");
            statement1.setString(1, unitName);
            ResultSet set = statement1.executeQuery();

            int unitId = -1;

            if (set.next()) {
                unitId = set.getInt("id");
            }

            if (isNewUnitName) {
                PreparedStatement statement2 = connection.prepareStatement("SELECT id FROM DEPARTMENTUNITS " +
                        "WHERE name = ?");
                statement2.setString(1, newName);
                set = statement1.executeQuery();

                int newUnitId = -1;

                if (set.next()) {
                    newUnitId = set.getInt("id");
                }

                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE EMPLOYEES " +
                                "SET unitid=? " +
                                "WHERE name=? AND unitid=?"
                );
                statement.setInt(1, newUnitId);
                statement.setString(2, name);
                statement.setInt(3, unitId);
                int exec = statement.executeUpdate();

                if (exec > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE EMPLOYEES " +
                                "SET name=? " +
                                "WHERE name=? AND unitid=?"
                );
                statement.setString(1, newName);
                statement.setString(2, name);
                statement.setInt(3, unitId);
                int exec = statement.executeUpdate();

                if (exec > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public boolean setEmployee(String name, String unitName, int lengthOfEmployment) {
        try {
            PreparedStatement statement1 = connection.prepareStatement("SELECT id FROM DEPARTMENTUNITS " +
                    "WHERE name = ?");
            statement1.setString(1, unitName);
            ResultSet set = statement1.executeQuery();

            int unitId = -1;

            if (set.next()) {
                unitId = set.getInt("id");
            }

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE EMPLOYEES " +
                            "SET lengthofemployment=? " +
                            "WHERE name=? AND unitid=?"
            );
            statement.setInt(1, lengthOfEmployment);
            statement.setString(2, name);
            statement.setInt(3, unitId);
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

            if (resultSet.next()) {
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
            PreparedStatement statement1 = connection.prepareStatement("SELECT id FROM DEPARTMENTUNITS " +
                    "WHERE name = ?");
            statement1.setString(1, unitName);
            ResultSet set = statement1.executeQuery();

            int unitId = -1;

            if (set.next()) {
                unitId = set.getInt("id");
            }

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM EMPLOYEES " +
                    "WHERE unitid=?");
            statement.setInt(1, unitId);

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

    public int countEmployees(String unitName) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) " +
                    "FROM DEPARTMENTUNITS INNER JOIN EMPLOYEES ON DEPARTMENTUNITS.id = EMPLOYEES.unitid " +
                    "GROUP BY departmentunits.name " +
                    "HAVING DEPARTMENTUNITS.name = ?");
            statement.setString(1, unitName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return -1;
        }
        return -1;
    }
}
