package chess.dao;

import chess.domain.piece.*;

import java.util.function.Function;
import java.util.stream.Stream;

public enum PieceMapper {
    BLACK_PAWN(BlackPawn.class, "pawn", Pawn::of),
    WHITE_PAWN(WhitePawn.class, "pawn", Pawn::of),
    BISHOP(Bishop.class, "bishop", Bishop::new),
    KNIGHT(Knight.class, "knight", Knight::new),
    QUEEN(Queen.class, "queen", Queen::new),
    KING(King.class, "king", King::new),
    ROOK(Rook.class, "rook", Rook::new),
    ;

    private final Class<? extends Piece> pieceType;
    private final String typeMessage;
    private final Function<Team, Piece> pieceMaker;

    PieceMapper(Class<? extends Piece> pieceType, String typeMessage, Function<Team, Piece> pieceMaker) {
        this.pieceType = pieceType;
        this.typeMessage = typeMessage;
        this.pieceMaker = pieceMaker;
    }

    public static String typeMessageOf(Piece piece) {
        return Stream.of(values())
                .filter(p -> p.pieceType.equals(piece.getClass()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("기물 데이터를 찾을 수 없습니다."))
                .typeMessage;
    }

    public static Piece findPieceByType(String typeMessage, Team team) {
        return Stream.of(values())
                .filter(p -> p.typeMessage.equals(typeMessage))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("기물 데이터를 찾을 수 없습니다."))
                .pieceMaker
                .apply(team);
    }

    public String getTypeMessage() {
        return typeMessage;
    }
}
