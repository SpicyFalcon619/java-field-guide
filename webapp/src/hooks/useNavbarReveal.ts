import { useEffect, useState, type RefObject } from 'react'

/**
 * A bar that steps out of the way when you scroll down a page and comes straight back when you scroll up, even a little.
 * Returns whether it is hidden right now.
 *
 * While it is enabled it also keeps the CSS variable `--navbar-offset` on the page at the bar's height (0 when it is
 * hidden), so other sticky parts (the code toolbar, the outline) can sit just under the bar instead of behind it.
 *
 * The bar is never hidden near the top of the page, and not while something inside it has the keyboard focus.
 */
export function useNavbarReveal(enabled: boolean, ref: RefObject<HTMLElement | null>): boolean {
  const [hidden, setHidden] = useState(false)

  useEffect(() => {
    const root = document.documentElement
    if (!enabled) {
      setHidden(false)
      root.style.removeProperty('--navbar-offset')
      return
    }

    let last = window.scrollY
    let frame = 0
    let isHidden = false
    const height = () => ref.current?.offsetHeight ?? 0
    const apply = (next: boolean) => {
      isHidden = next
      setHidden(next)
      root.style.setProperty('--navbar-offset', next ? '0px' : `${height()}px`)
    }
    apply(false)

    const update = () => {
      frame = 0
      const y = Math.max(0, window.scrollY)
      const delta = y - last
      if (Math.abs(delta) < 4) return // ignore jitter
      last = y
      if (delta > 0) {
        if (y > height() && !ref.current?.contains(document.activeElement)) apply(true)
      } else if (isHidden || y <= height()) {
        apply(false)
      }
    }
    const onScroll = () => {
      if (!frame) frame = requestAnimationFrame(update)
    }
    // The bar's height can change (the window gets narrower, the font loads): keep the offset right while it is shown.
    const onResize = () => {
      if (!isHidden) root.style.setProperty('--navbar-offset', `${height()}px`)
    }
    window.addEventListener('scroll', onScroll, { passive: true })
    window.addEventListener('resize', onResize)
    return () => {
      window.removeEventListener('scroll', onScroll)
      window.removeEventListener('resize', onResize)
      cancelAnimationFrame(frame)
      root.style.removeProperty('--navbar-offset')
    }
  }, [enabled, ref])

  return hidden
}
