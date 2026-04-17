package com.universidad.antipatrones;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Version inicial con antipatron God Object conservada para el laboratorio.
public class GestorBiblioteca {

    private final List<String[]> libros = new ArrayList<>();
    private final List<String[]> socios = new ArrayList<>();
    private final List<String[]> prestamos = new ArrayList<>();

    public void agregarLibro(String id, String titulo, String autor) {
        libros.add(new String[] {id, titulo, autor, "true"});
        System.out.println("Libro agregado: " + titulo);
    }

    public Optional<String[]> buscarLibro(String id) {
        return libros.stream().filter(l -> l[0].equals(id)).findFirst();
    }

    public List<String[]> listarLibrosDisponibles() {
        return libros.stream().filter(l -> l[3].equals("true")).toList();
    }

    public void registrarSocio(String id, String nombre, String email) {
        if (!email.contains("@")) {
            System.out.println("Email invalido");
            return;
        }
        socios.add(new String[] {id, nombre, email});
        System.out.println("Socio registrado: " + nombre);
    }

    public boolean socioExiste(String id) {
        return socios.stream().anyMatch(s -> s[0].equals(id));
    }

    public void realizarPrestamo(String libroId, String socioId) {
        Optional<String[]> libro = buscarLibro(libroId);
        if (libro.isEmpty()) {
            System.out.println("Libro no encontrado");
            return;
        }
        if (!libro.get()[3].equals("true")) {
            System.out.println("Libro no disponible");
            return;
        }
        if (!socioExiste(socioId)) {
            System.out.println("Socio no registrado");
            return;
        }
        libro.get()[3] = "false";
        prestamos.add(new String[] {libroId, socioId, "2026-03-17"});
        System.out.println("Prestamo realizado: libro " + libroId + " a socio " + socioId);
    }

    public void devolverLibro(String libroId) {
        Optional<String[]> libro = buscarLibro(libroId);
        libro.ifPresent(l -> {
            l[3] = "true";
            System.out.println("Libro devuelto: " + l[1]);
        });
    }

    public void imprimirReporteCompleto() {
        System.out.println("=== REPORTE BIBLIOTECA ===");
        System.out.println("Libros registrados: " + libros.size());
        System.out.println("Libros disponibles: " + listarLibrosDisponibles().size());
        System.out.println("Socios registrados: " + socios.size());
        System.out.println("Prestamos activos: " + prestamos.size());
        System.out.println("==========================");
    }
}
