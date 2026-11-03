import type { ReactNode } from 'react';

/** Iconos de trazo (24x24) dibujados en línea para no depender de librerías externas. */
const PATHS: Record<string, ReactNode> = {
  home: <path d="M3 11l9-7 9 7v9a1 1 0 0 1-1 1h-5v-6H9v6H4a1 1 0 0 1-1-1z" />,
  clipboard: (
    <>
      <rect x="8" y="2" width="8" height="4" rx="1" />
      <path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2M9 12h6M9 16h4" />
    </>
  ),
  calculator: (
    <>
      <rect x="4" y="2" width="16" height="20" rx="2" />
      <path d="M8 6h8M8 11h.01M12 11h.01M16 11h.01M8 15h.01M12 15h.01M16 15v4M8 19h4" />
    </>
  ),
  package: <path d="M21 8l-9-5-9 5v8l9 5 9-5zM3 8l9 5 9-5M12 13v8M7.5 5.5l9 5" />,
  file: <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8zM14 2v6h6M8 13h8M8 17h5" />,
  fileCheck: <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8zM14 2v6h6M9 15l2 2 4-4" />,
  alert: <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2zM12 7v4M12 14h.01" />,
  truck: (
    <>
      <path d="M1 5h13v11H1zM14 9h4.5L22 12.5V16h-8" />
      <circle cx="6" cy="18" r="2" />
      <circle cx="18" cy="18" r="2" />
    </>
  ),
  send: <path d="M22 2L11 13M22 2l-7 20-4-9-9-4z" />,
  route: (
    <>
      <circle cx="6" cy="19" r="2" />
      <circle cx="18" cy="5" r="2" />
      <path d="M8 19h8.5a3.5 3.5 0 0 0 0-7h-9a3.5 3.5 0 0 1 0-7H16" />
    </>
  ),
  flag: <path d="M4 22V4M4 4h13l-2 4 2 4H4" />,
  activity: <path d="M22 12h-4l-3 9L9 3l-3 9H2" />,
  wallet: <path d="M20 7V4H5a2 2 0 0 0 0 4h15v12H5a2 2 0 0 1-2-2V6M16 14h.01" />,
  banknote: (
    <>
      <rect x="2" y="6" width="20" height="12" rx="2" />
      <circle cx="12" cy="12" r="2.5" />
      <path d="M6 12h.01M18 12h.01" />
    </>
  ),
  receipt: <path d="M5 2v20l2.5-1.5L10 22l2.5-1.5L15 22l2.5-1.5L20 22V2l-2.5 1.5L15 2l-2.5 1.5L10 2 7.5 3.5zM9 8h6M9 12h6M9 16h4" />,
  sliders: <path d="M4 21v-7M4 10V3M12 21v-9M12 8V3M20 21v-5M20 12V3M1 14h6M9 8h6M17 16h6" />,
  printer: (
    <>
      <path d="M6 9V2h12v7M6 18H4a2 2 0 0 1-2-2v-5a2 2 0 0 1 2-2h16a2 2 0 0 1 2 2v5a2 2 0 0 1-2 2h-2" />
      <rect x="6" y="14" width="12" height="8" />
    </>
  ),
  users: (
    <>
      <circle cx="9" cy="7" r="4" />
      <path d="M2 21v-1a6 6 0 0 1 12 0v1M16 3.13a4 4 0 0 1 0 7.75M22 21v-1a6 6 0 0 0-4-5.66" />
    </>
  ),
  user: (
    <>
      <circle cx="12" cy="8" r="4" />
      <path d="M4 21v-1a7 7 0 0 1 16 0v1" />
    </>
  ),
  logout: <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4M16 17l5-5-5-5M21 12H9" />,
  menu: <path d="M3 6h18M3 12h18M3 18h18" />,
  plus: <path d="M12 5v14M5 12h14" />,
  chevron: <path d="M9 18l6-6-6-6" />,
  arrowRight: <path d="M5 12h14M13 6l6 6-6 6" />,
  calendar: (
    <>
      <rect x="3" y="4" width="18" height="18" rx="2" />
      <path d="M16 2v4M8 2v4M3 10h18" />
    </>
  ),
  layers: <path d="M12 2l10 5-10 5L2 7zM2 17l10 5 10-5M2 12l10 5 10-5" />,
  pin: (
    <>
      <path d="M20 10c0 6-8 12-8 12s-8-6-8-12a8 8 0 0 1 16 0z" />
      <circle cx="12" cy="10" r="3" />
    </>
  ),
  shield: <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10zM9 12l2 2 4-4" />,
};

export type IconName = keyof typeof PATHS;

export function Icon({ name, size = 18, className }: { name: IconName; size?: number; className?: string }) {
  return (
    <svg
      className={className}
      width={size}
      height={size}
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="1.8"
      strokeLinecap="round"
      strokeLinejoin="round"
      aria-hidden="true"
    >
      {PATHS[name]}
    </svg>
  );
}

/** Icono de cada pantalla de documentos (clave de docs.ts). */
export const DOC_ICONS: Record<string, IconName> = {
  pedidos: 'clipboard',
  cotizaciones: 'calculator',
  'pedidos-flete': 'package',
  crt: 'file',
  reclamos: 'alert',
  asignaciones: 'truck',
  salidas: 'send',
  viajes: 'route',
  llegadas: 'flag',
  eventos: 'activity',
  cajas: 'wallet',
  viaticos: 'banknote',
  comprobantes: 'receipt',
  rendiciones: 'fileCheck',
};

export const MODULE_ICONS: Record<string, IconName> = {
  Pedidos: 'package',
  'Logística': 'truck',
  Rendiciones: 'wallet',
};

/** Marca: camión sobre fondo naranja. */
export function BrandMark({ size = 34 }: { size?: number }) {
  return (
    <svg width={size} height={size} viewBox="0 0 40 40" aria-hidden="true">
      <rect width="40" height="40" rx="10" fill="var(--accent)" />
      <path d="M7 13h15v12H7zM22 17h5.5l4.5 4.5V25H22" fill="none" stroke="#0c2340" strokeWidth="2.4" strokeLinejoin="round" />
      <circle cx="12.5" cy="27.5" r="2.6" fill="#0c2340" />
      <circle cx="27" cy="27.5" r="2.6" fill="#0c2340" />
    </svg>
  );
}
