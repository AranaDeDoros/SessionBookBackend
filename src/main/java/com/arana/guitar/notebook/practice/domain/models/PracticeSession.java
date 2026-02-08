package com.arana.guitar.notebook.practice.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "practice_sessions")
public class PracticeSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Internal DB ID

    @Column(nullable = false, unique = true, updatable = false)
    private String publicId = UUID.randomUUID().toString();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "session",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SessionSong> songs = new ArrayList<>();

    public PracticeSession(User owner, String name) {
        this.owner = owner;
        this.name = name;
    }
    public void addSong(Song song, int position) {
        this.songs.add(new SessionSong(this, song, position));
    }

    public void replaceSongs(@NotEmpty List<Song> newSongs) {
        this.songs.clear();
        for (int i = 0; i < newSongs.size(); i++) {
            addSong(newSongs.get(i), i);
        }
    }
}
