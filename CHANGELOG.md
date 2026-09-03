# Changelog

## [Unreleased]

### Added

- Public Android v4 usage, sensor, battery, and acknowledgment contracts now document the exact device-bound authentication and consent state used by selfhost deployments. [#33](https://github.com/uzaira0/chronicle-api/pull/33)
- Versioned enrollment previews let participants review the responsible study, consent terms, and exact data scope before a one-time invitation is consumed. [#31](https://github.com/uzaira0/chronicle-api/pull/31)
- Replay-safe mobile enrollment headers bind a client-generated attempt and proposed per-device credential to the one-time invitation, allowing an interrupted enrollment to converge without storing a recoverable raw key on the server. [#31](https://github.com/uzaira0/chronicle-api/pull/31)
- Durable mobile withdrawal request IDs are now a required client-generated header, so interrupted erasure requests can retry against the exact credential-bound receipt. [#31](https://github.com/uzaira0/chronicle-api/pull/31)
