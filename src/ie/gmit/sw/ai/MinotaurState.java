package ie.gmit.sw.ai;

/**
 * Having a state enumeration was an ideal
 * method for intuitive logic handling. Although slightly
 * verbose and maybe unneccesary, it certainly helped to
 * clarify and made handling state changes easier.
 */
public enum MinotaurState {
	ATTACK,
	RUN,
	PATROL,
	DEAD
}
